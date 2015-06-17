package com.business.domain;


import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by AlexBob on 2015-5-28.
 */
@Entity
@Table(name = "customers")
public class Customer extends CustomerUser {

    private String name;
    private String consignee;
    private String phone;
    private String address;
    private String email;
    private String fax;
    private String area;

    public Customer() {
        super();
    }

    public Customer(String username, String password, String phone, String email) {
        super(username, password, true);
        this.phone = phone;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
