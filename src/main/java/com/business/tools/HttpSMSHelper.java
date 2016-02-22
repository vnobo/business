package com.business.tools;

import com.business.config.AutoSMSConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by billb on 2015-04-21.
 */
public class HttpSMSHelper {

    private static final Log log = LogFactory.getLog(HttpSMSHelper.class);

    private static String CORP_ID;
    private static String PASSWORD ;
    private static String LOGIN_NAME;
    private static String SMS_URL;
    private static List<String> phoneList = new ArrayList<>();

    private static String url;
    private static String phoneNoStr;


    @Autowired
    public HttpSMSHelper(AutoSMSConfiguration smsConfig) {
        CORP_ID = smsConfig.getCorId();
        PASSWORD = smsConfig.getPassword();
        LOGIN_NAME = smsConfig.getLoginName();
        SMS_URL = smsConfig.getSmsUrl();
        phoneList = smsConfig.getPhones();
    }

    public static void runTaskSend(String context) {
        Runnable task = () -> {
            String threadName = Thread.currentThread().getName();
            log.info("短信开始发送=========================》");
            smsSend(context).forEach(p -> log.info("返回状态：" + p));
            log.info("SMS Send Task is: " + threadName + " Url:" + url);
            log.info("短信发送结束=========================》");
        };
        Thread thread = new Thread(task);
        thread.start();
    }


    public static Stream<String> smsSend(String context) {
        Stream<String> res = null;
        initUrl(context);
        System.out.println(CORP_ID);
        System.out.println(PASSWORD);
        System.out.println(LOGIN_NAME);
        System.out.println(SMS_URL);
        System.out.println(phoneList.toString());
        System.out.println(phoneNoStr);
        System.out.println(url);
       /* try {
            initUrl(phoneList, context);
            URL U = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) U.openConnection();
            connection.setUseCaches(false);
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            res = reader.lines();

        } catch (IOException e) {
            log.error("链接失败：", e);
        }
        */
        return res;
    }

    private static void initUrl(String context) {
        phoneNoStr = String.join(";", phoneList);
        HttpSMSHelper.url = MessageFormat.format(SMS_URL,
                CORP_ID, LOGIN_NAME, PASSWORD, phoneNoStr, 1, context);
    }
}
