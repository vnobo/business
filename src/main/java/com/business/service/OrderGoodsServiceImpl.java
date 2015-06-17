package com.business.service;

import com.business.domain.OrderGoods;
import com.business.domain.repository.OrderGoodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * Created by billb on 2015-04-07.
 */
@Service
public class OrderGoodsServiceImpl implements OrderGoodsService {


    public final String DEF_CREATE_ORDERGOODS_LOG_SQL = "insert into Order_Goods_log(goodsid,Editor,edit_type) VALUES (?,?,?)";

    private JdbcTemplate jdbcTemplate;
    private OrderGoodsRepository orderGoodsRepository;

    private String createOrderGoodsLogSql;


    @Autowired
    public OrderGoodsServiceImpl(JdbcTemplate dataSource, OrderGoodsRepository orderGoodsRepository) {
        this.jdbcTemplate = dataSource;
        this.orderGoodsRepository = orderGoodsRepository;
        this.createOrderGoodsLogSql = DEF_CREATE_ORDERGOODS_LOG_SQL;
    }


    public void addOrderGoods(OrderGoods goods) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            if (orderGoodsRepository.findByGoodsid(goods.getGId()) == null) {
                goods.setEditor(((UserDetails) principal).getUsername());
                jdbcTemplate.update(createOrderGoodsLogSql, goods.getGId(), goods.getEditor(), "A");
                System.out.println(createOrderGoodsLogSql);
                orderGoodsRepository.save(goods);
            }
        }
    }

    public void deleteOrderGoods(int id) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            if (orderGoodsRepository.findByGoodsid(id) != null) {
                jdbcTemplate.update(createOrderGoodsLogSql, id, ((UserDetails) principal).getUsername(), "D");
                System.out.println(createOrderGoodsLogSql);
                orderGoodsRepository.delete(id);
            }
        }
    }

    public void editOrderGoods(OrderGoods goods) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            if (orderGoodsRepository.findByGoodsid(goods.getGId()) != null) {
                goods.setEditor(((UserDetails) principal).getUsername());
                jdbcTemplate.update(createOrderGoodsLogSql, goods.getGId(), ((UserDetails) principal).getUsername(), "U");
                System.out.println(createOrderGoodsLogSql);
                orderGoodsRepository.save(goods);
            }
        }
    }


}
