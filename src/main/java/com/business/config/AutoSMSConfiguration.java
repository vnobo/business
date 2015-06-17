package com.business.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by billb on 2015-05-20.
 */

@Component
@ConfigurationProperties(prefix="sms")
public class AutoSMSConfiguration {
    private static String[] phones;

    public String[] getPhones() {
        return phones;
    }

    public void setPhones(String[] phones) {
        this.phones = phones;
    }


}
