package com.business.web;

import com.business.domain.OrderGoods;
import com.business.service.OrderGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by billb on 2015-04-07.
 */

@RestController
public class OrderGoodsController {

    private OrderGoodsService orderGoodsService;

    @Autowired
    public OrderGoodsController(OrderGoodsService orderGoodsService) {
        this.orderGoodsService = orderGoodsService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN_REPGOODS')")
    @RequestMapping("/editOrderGoods")
    public Map<String, Object> editOrderGoods(@RequestBody OrderGoods goods) {
        Map<String, Object> model = new HashMap<>();
        orderGoodsService.editOrderGoods(goods);
        model.put("id", "LE200");
        model.put("content", "商品信息修改成功！");
        model.put("status", "200");
        return model;
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRATORS')")
    @RequestMapping("/deleteOrderGoods")
    public Map<String, Object> deleteOrderGoods(Integer id) {
        Map<String, Object> model = new HashMap<>();
        orderGoodsService.deleteOrderGoods(id);
        model.put("id", "LE200");
        model.put("content", "删除成功！");
        model.put("status", "200");
        return model;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN_GOODS')")
    @RequestMapping("/addOrderGoods")
    public Map<String, Object> addOrderGoods(@RequestBody OrderGoods goods) {
        Map<String, Object> model = new HashMap<>();
        orderGoodsService.addOrderGoods(goods);
        model.put("id", "LE200");
        model.put("content", "商品增加成功！");
        model.put("status", "200");
        return model;
    }
}
