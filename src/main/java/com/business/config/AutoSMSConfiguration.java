package com.business.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by billb on 2015-05-20.
 */

@Component
@ConfigurationProperties(prefix="sms")
public class AutoSMSConfiguration {

    private  String corId = "302003";
    private  String password = "K9IwtKCq8MBkg";
    private  String loginName = "Admin";
    private  String smsUrl = "http://116.204.35.93:81/SDK/Sms_Send.asp?CorpID={0}&LoginName={1}&passwd={2}&send_no={3}&LongSms={4}&msg={5}";

    private List<String> phones = new ArrayList<String>();

    public String getCorId() {
        return corId;
    }

    public String getPassword() {
        return password;
    }

    public String getLoginName() {
        return loginName;
    }

    public List<String> getPhones() {
        return phones;
    }

    public String getSmsUrl() {
        return smsUrl;
    }
}
