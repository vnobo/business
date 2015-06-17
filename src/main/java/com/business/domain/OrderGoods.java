package com.business.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

/**
 * Created by billb on 2015-04-24.
 */
@Entity
@Table(name = "order_goods")
public class OrderGoods {

    @Id
    private int goodsid;

    private String editor;

    private Timestamp lastDateTime = Timestamp.from(Instant.now());

    private double price;

    @OneToOne
    @JoinColumn(name = "goodsid")
    private Goods paramGoods;

    @ManyToOne
    @JoinColumn(name = "deptid")
    private DeptList paramDept = new DeptList();


    public Timestamp getLastDateTime() {
        return lastDateTime;
    }

    public void setLastDateTime(Timestamp lastDateTime) {
        this.lastDateTime = lastDateTime;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public int getGId() {return goodsid;}

    public void setGId(int goodsid) {this.goodsid = goodsid;}

    public DeptList getDept() {return paramDept;}
    public void setDept(DeptList paramDept) {this.paramDept = paramDept;}

    public Goods getGoods() {return paramGoods;}

    public void setGoods(Goods paramGoods) {this.paramGoods = paramGoods;}


    public String toString() {
        return "OrderGoods{" +
                "goodsid=" + goodsid +
                ", editor='" + editor + '\'' +
                ", lastDateTime=" + lastDateTime +
                ", price=" + price +
                ", paramGoods=" + paramGoods.getName() +
                ", paramDept=" + paramDept.getName() +
                '}';
    }
}
