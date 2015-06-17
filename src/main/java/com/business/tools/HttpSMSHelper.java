package com.business.tools;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by billb on 2015-04-21.
 */
public class HttpSMSHelper {

    private static final Log log = LogFactory.getLog(HttpSMSHelper.class);
    public final static String CORP_ID = "302003";
    public final static String PASSWORD = "K9IwtKCq8MBkg";
    public final static String LOGIN_NAME = "Admin";
    public final static String SMS_URL = "http://sms3.mobset.com/SDK/Sms_Send.asp?CorpID={0}&LoginName={1}&passwd={2}&send_no={3}&LongSms={4}&msg={5}";

    private static String url;
    private static String phoneNoStr;

    public static void runTaskSend(List<String> phoneList, String context) {
        Runnable task = () -> {
            String threadName = Thread.currentThread().getName();
            log.info("短信开始发送=========================》");
            smsSend(phoneList, context).forEach(p->log.info("返回状态："+p));
            log.info("SMS Send Task is: " + threadName + " Url:" + url);
            log.info("短信发送结束=========================》");
        };
        Thread thread = new Thread(task);
        thread.start();
    }


    public static Stream<String> smsSend(List<String> phoneList, String context) {
        Stream<String> res = null;
        try {
            initUrl(phoneList,context);
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

    private static void initUrl(List<String> phoneList,String context) {
        phoneNoStr=String.join(";",phoneList);
        HttpSMSHelper.url = MessageFormat.format(SMS_URL,
                CORP_ID, LOGIN_NAME,PASSWORD, phoneNoStr,1, context);
    }
}
