package com.business.domain;

import javax.persistence.*;

/**
 * Created by billb on 2015-05-04.
 */
@Entity
@Table(name = "purchaseitem")
public class QueryPurItem {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "sheetid")
    private QueryPur queryPur;

    private int goodsid;
    @Column(name = "goodsname")
    private String name;
    private double price;
    private double cost;
    private double qty;
    private double valqty;
    private double retqty;


    public int getGoodsid() {
        return goodsid;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public double getCost() {
        return cost;
    }

    public double getQty() {
        return qty;
    }

    public double getValqty() {
        return valqty;
    }

    public double getRetqty() {
        return retqty;
    }

    public long getId() {
        return id;
    }

    public QueryPur getSheet() {
        return queryPur;
    }
}
