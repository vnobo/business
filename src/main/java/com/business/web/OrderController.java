package com.business.web;

import com.business.domain.Purchase;
import com.business.service.OrderService;
import com.business.service.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by billb on 2015-04-10.
 */
@RestController
public class OrderController {

    private OrderService orderService;

    @Autowired
    public OrderController(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }


    @RequestMapping(value = "/saveUserOrder")
    public Map<String, Object> saveUserOrder(@RequestBody Purchase purchase) {
        Map<String, Object> model = new HashMap<>();
        orderService.seavOrder(purchase);
        model.put("id", "LE200");
        model.put("content", "订单保存成功！");
        model.put("status", "200");
        return model;
    }


    @PreAuthorize("hasRole('ROLE_ADMINISTRATORS')")
    @RequestMapping("/verifysheet")
    public Map<String, Object> verifySheet(@RequestParam("sheetid") String sheetid) {
        Map<String, Object> model = new HashMap<>();
        orderService.verifyOrderSheet(sheetid);
        model.put("id", "LE200");
        model.put("content", "订单操作成功！");
        model.put("status", "200");
        return model;
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRATORS')")
    @RequestMapping("/cancelorders")
    public Map<String, Object> cancelOrders(@RequestParam("sheetid") String sheetid) {
        Map<String, Object> model = new HashMap<>();
        orderService.cancelOrdersBySheetId(sheetid);
        model.put("id", "LE200");
        model.put("content", "订单注销成功！");
        model.put("status", "200");
        return model;
    }
}
