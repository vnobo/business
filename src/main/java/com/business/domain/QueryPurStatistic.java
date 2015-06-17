package com.business.domain;

/**
 * Created by billb on 2015/6/4.
 */
public class QueryPurStatistic {
    private int goodsId;
    private String name;
    private double price;
    private double countQty;
    private double countValQty;
    private double countRetQty;

    public QueryPurStatistic() {

    }

    public QueryPurStatistic(int goodsId, String name, double price, double countQty, double countValQty, double countRetQty) {
        this.goodsId = goodsId;
        this.name = name;
        this.price = price;
        this.countQty = countQty;
        this.countValQty = countValQty;
        this.countRetQty = countRetQty;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public double getCountQty() {
        return countQty;
    }

    public double getCountValQty() {
        return countValQty;
    }

    public double getCountRetQty() {
        return countRetQty;
    }
}
