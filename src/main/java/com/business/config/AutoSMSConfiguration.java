package com.business.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by billb on 2015-05-20.
 */
@Component
@ConfigurationProperties(prefix = "sms")
public class AutoSMSConfiguration {

    @NotNull
    private String corid;

    @NotNull
    private String password;

    @NotNull
    private String loginname;

    @NotNull
    private String smsurl;

    @NotNull
    private List<String> phones = new ArrayList<String>();

    public String getCorid() {
        return corid;
    }

    public String getPassword() {
        return password;
    }

    public String getLoginname() {
        return loginname;
    }

    public String getSmsurl() {
        return smsurl;
    }

    public List<String> getPhones() {
        return phones;
    }

    public void setCorid(String corid) {
        this.corid = corid;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public void setSmsurl(String smsurl) {
        this.smsurl = smsurl;
    }

    public void setPhones(List<String> phones) {
        this.phones = phones;
    }
}
