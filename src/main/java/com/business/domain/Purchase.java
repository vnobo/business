package com.business.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Set;

/**
 * Created by billb on 2015-04-15.
 */
@Entity(name = "purchase")
public class Purchase {

    @Id
    private String sheetid;
    private String refsheetid;
    private String khbank;
    private String accno;

    @JoinColumn(nullable = false)
    private String phone;
    private String area;

    @JoinColumn(nullable = false)
    private String email;

    @JoinColumn(nullable = false)
    private String address;

    @Column(name = "Editor", nullable = false)
    private String username;

    @JoinColumn(nullable = false)
    private String consignee;
    private Timestamp editdate = Timestamp.from(Instant.now());
    private String hander;
    private Timestamp handdate;
    private String checker;
    private Timestamp checkdate = Timestamp.from(Instant.now());
    private int flag;
    private String note;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "sheetid")
    private Set<PurchaseItem> items;

    public String getSheet() {
        return sheetid;
    }

    public void setSheet(String sheetid) {
        this.sheetid = sheetid;
    }

    public String getRefsheetid() {
        return refsheetid;
    }

    public void setRefsheetid(String refsheetid) {
        this.refsheetid = refsheetid;
    }


    public String getKhbank() {
        return khbank;
    }

    public void setKhbank(String khbank) {
        this.khbank = khbank;
    }

    public String getAccno() {
        return accno;
    }

    public void setAccno(String accno) {
        this.accno = accno;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUName() {
        return username;
    }

    public void setUName(String username) {
        this.username = username;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }


    public String getHander() {
        return hander;
    }

    public void setHander(String hander) {
        this.hander = hander;
    }

    public Timestamp getHanddate() {
        return handdate;
    }

    public void setHanddate(Timestamp handdate) {
        this.handdate = handdate;
    }

    public String getChecker() {
        return checker;
    }

    public void setChecker(String checker) {
        this.checker = checker;
    }

    public Timestamp getEditdate() {
        return editdate;
    }

    public void setEditdate(Timestamp editdate) {
        this.editdate = editdate;
    }

    public Timestamp getCheckdate() {
        return checkdate;
    }

    public void setCheckdate(Timestamp checkdate) {
        this.checkdate = checkdate;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Set<PurchaseItem> getItems() {
        return items;
    }

    public void setItems(Set<PurchaseItem> items) {
        this.items = items;
    }
}
