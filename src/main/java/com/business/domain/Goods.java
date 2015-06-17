package com.business.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by billb on 2015-04-23.
 */
@Entity
@Table(name = "goods")
public class Goods {

    @Id
    @Column(insertable = false)
    private int goodsid;

    @Column(insertable = false)
    private String name;

    @Column(name = "BarcodeID", insertable = false)
    private String barcode;

    @Column(insertable = false)
    private int flag;

    @Column(insertable = false)
    private String unitname;

    public int getGId() {
        return goodsid;
    }

    public String getName() {
        return name;
    }

    public String getBarcode() {
        return barcode;
    }

    public int getFlag() {
        return flag;
    }

    public String getUnitname() {
        return unitname;
    }


}
