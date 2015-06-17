package com.business.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

/**
 * Created by billb on 2015-05-05.
 */
@Entity(name = "order_goods_log")
public class OrderGoodsLog {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "goodsid")
    private Goods paramGoods;

    private String editor;

    @Column(name = "lastdatetime")
    private Timestamp lastDateTime = Timestamp.from(Instant.now());


    @Column(name = "edit_type")
    private String editType;

    public long getId() {
        return id;
    }

    public Goods getGoods() {
        return paramGoods;
    }

    public String getEditor() {
        return editor;
    }


    public Timestamp getLastDateTime() {
        return lastDateTime;
    }


    public String getEditType() {
        return editType;
    }

}
