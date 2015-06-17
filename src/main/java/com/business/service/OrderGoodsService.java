package com.business.service;

import com.business.domain.OrderGoods;

/**
 * Created by billb on 2015-04-07.
 */
public interface OrderGoodsService {


    void addOrderGoods(OrderGoods goods);

    void deleteOrderGoods(int id);

    void editOrderGoods(OrderGoods goods);

}
