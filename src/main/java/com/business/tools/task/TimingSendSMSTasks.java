package com.business.tools.task;

import com.business.config.AutoSMSConfiguration;
import com.business.domain.DeptList;
import com.business.domain.OrderGoods;
import com.business.domain.QueryPurItem;
import com.business.domain.repository.DeptListRepository;
import com.business.domain.repository.OrderGoodsRepository;
import com.business.service.QueryPurService;
import com.business.service.QueryPurServiceImpl;
import com.business.tools.HttpSMSHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by billbob on 2015-04-27.
 */
@Component
public class TimingSendSMSTasks {
    private static final Log log = LogFactory.getLog(TimingSendSMSTasks.class);

    private QueryPurService purService;
    private DeptListRepository deptReposi;
    private OrderGoodsRepository goodsRepository;

    private String smsContext ="你好，今日销售数据为:";

    @Autowired
    public TimingSendSMSTasks(QueryPurServiceImpl purService,
                              DeptListRepository deptListRepository,
                              OrderGoodsRepository goodsRepository) {
        this.purService = purService;
        this.deptReposi = deptListRepository;
        this.goodsRepository=goodsRepository;
    }


    //@Scheduled(cron = "0 0 21 * * ?")
    @Scheduled(cron="0/5 * *  * * ?")
    public void todayPurSumSMS() {
        log.info("定时任务开始！");
        Map<String, Object> params = new HashMap<>();
        params.put("start", LocalDate.now().toString() + " 00:00:00");
        params.put("end", LocalDate.now().toString() + " 23:59:59");
        params.put("flag", 100);
        List<QueryPurItem> purAll = purService.findAllPurToday(params);
        Iterable<DeptList> deptList = deptReposi.findAll();
        List<String> sumPriceStr= new ArrayList<>();
        List<Double> sumPrice=new ArrayList<>();
        deptList.forEach(d -> {
            if (d.getDeptId() != 999999) {
                List<Integer> goodsList =goodsRepository.findByParamDeptId(d.getDeptId()).stream().map(OrderGoods::getGId).collect(Collectors.toList());
                double sumQty = purAll.stream().filter(p -> goodsList.contains(p.getGoodsid()))
                        .mapToDouble(p -> p.getQty() * p.getPrice()).sum();
                sumPrice.add(sumQty);
                sumPriceStr.add(d.getName() + String.format("%.2f", sumQty));
            }
        });
        sumPriceStr.add("合计"+ String.format("%.2f", sumPrice.stream().mapToDouble(p->p.doubleValue()).sum()));
        log.info("发送短信内容："+smsContext);
        HttpSMSHelper.runTaskSend(smsContext);
        smsContext = "你好，今日销售数据为:";
        log.info("定时任务结束！");
    }
}
