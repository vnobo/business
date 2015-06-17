package com.business;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Created by billb on 2015-05-25.
 */
public class test {
    public static void main(String agr[]){
        BCryptPasswordEncoder bCryptEncoder = new BCryptPasswordEncoder();
       System.out.println(bCryptEncoder.encode("123456"));
    }

}
