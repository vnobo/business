package com.business.tools;

import com.business.config.AutoSMSConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.MessageFormat;
import java.util.stream.Stream;

/**
 * Created by billb on 2015-04-21.
 */
@Component
public class HttpSMSHelper {

    private final Log log = LogFactory.getLog(HttpSMSHelper.class);

    private String CORP_ID;
    private String PASSWORD;
    private String LOGIN_NAME;
    private String SMS_URL;

    private String url;
    private String phoneNoStr;

    @Autowired
    public HttpSMSHelper(AutoSMSConfiguration smsConfig) {
        CORP_ID = smsConfig.getCorid();
        PASSWORD = smsConfig.getPassword();
        LOGIN_NAME = smsConfig.getLoginname();
        SMS_URL = smsConfig.getSmsurl();
        phoneNoStr = String.join(";", smsConfig.getPhones());

    }

    public void runTaskSend(String context) {
        this.url = MessageFormat.format(SMS_URL, CORP_ID, LOGIN_NAME, PASSWORD, phoneNoStr, 1, context);
        ThreadSend();
    }

    public void runUserSend(String phone,String context) {
        this.url = MessageFormat.format(SMS_URL, CORP_ID, LOGIN_NAME, PASSWORD, phone, 1, context);
        ThreadSend();
    }

    private void ThreadSend(){
        Runnable task = () -> {
            String threadName = Thread.currentThread().getName();
            log.info("短信开始发送=========================》");
            smsSend().forEach(p -> log.info("返回状态：" + p));
            log.info("SMS Send Task is: " + threadName + " Url:" + url);
            log.info("短信发送结束=========================》");
        };
        Thread thread = new Thread(task);
        thread.start();
    }

    private Stream<String> smsSend() {
        Stream<String> res = null;
        try {
            URL U = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) U.openConnection();
            connection.setUseCaches(false);
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            res = reader.lines();

        } catch (IOException e) {
            log.error("链接失败：", e);
        }
        return res;
    }

}
