package com.business.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by billb on 2015-04-15.
 */
@Entity(name = "purchaseitem")
public class PurchaseItem {

    @Id
    @GeneratedValue
    private long id;

    private String sheetid;

    @Column(nullable = false)
    private int goodsid;

    @Column(name = "goodsname", nullable = false)
    private String name;

    @Column(nullable = false)
    private String barcode;
    private double price;
    private double cost;

    @Column(nullable = false)
    private double qty;

    private double valqty;
    private double retqty;

    private double sendqty;

    @Column(nullable = false)
    private String pknum;
    private String memo;
    private int promflag;


    public String getSheetid() {return sheetid;}

    public void setSheetid(String sheetid) {this.sheetid = sheetid;}

    public int getGoodsid() {return goodsid;}

    public void setGoodsid(int goodsid) {this.goodsid = goodsid;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }

    public double getValqty() {
        return valqty;
    }

    public void setValqty(double valqty) {
        this.valqty = valqty;
    }

    public String getPknum() {
        return pknum;
    }

    public void setPknum(String pknum) {
        this.pknum = pknum;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public int getPromflag() {
        return promflag;
    }

    public void setPromflag(int promflag) {
        this.promflag = promflag;
    }

    public double getRetqty() {
        return retqty;
    }

    public void setRetqty(double retqty) {
        this.retqty = retqty;
    }

    public long getItemId() {
        return id;
    }

    public double getSendqty() {
        return sendqty;
    }

    public void setSendqty(double sendqty) {
        this.sendqty = sendqty;
    }
}
