package com.business.service;

import com.business.domain.Purchase;
import com.business.domain.repository.PurchaseRepository;
import com.business.tools.HttpSMSHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by billb on 2015-04-10.
 */
@Service
public class OrderServiceImpl implements OrderService {

    private JdbcTemplate jdbcTemplate;
    private PurchaseRepository purchaseRepository;

    @Autowired
    public OrderServiceImpl(JdbcTemplate jdbcTemplate, PurchaseRepository purchaseRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.purchaseRepository = purchaseRepository;
    }


    public void seavOrder(Purchase purchase) {
        Purchase purOld= purchaseRepository.findOne(purchase.getSheet());
        if(purOld!=null){
            purchaseRepository.delete(purOld);
        }
        purchaseRepository.save(purchase);
    }

    public void verifyOrderSheet(String sheetId) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String storedProc = "exec ST_Purchase '" + sheetId + "','" + ((UserDetails) principal).getUsername() + "'";
            jdbcTemplate.execute(storedProc);
            System.out.println("Exec Verify SQL:" + storedProc);
        }
        Purchase purchase = purchaseRepository.findOne(sheetId);
        if (purchase.getFlag() == 3 && !purchase.getPhone().isEmpty()) {
            String context = "你好，你的订单号为:" + sheetId + "已经出库，请注意验收。谢谢惠顾！";
            List<String> phoneList = new ArrayList<>();
            phoneList.add(purchase.getPhone());
            HttpSMSHelper.runTaskSend(phoneList, context);
        }
    }

    public void cancelOrdersBySheetId(String sheetId) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String storedProc = "exec ST_PurchaseCancel '" + sheetId + "','" + ((UserDetails) principal).getUsername() + "'";
            jdbcTemplate.execute(storedProc);
        }
    }

}
