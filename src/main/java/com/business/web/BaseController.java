package com.business.web;

import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

/**
 * Created by billb on 2015-03-26.
 */

public class BaseController {

    @RequestMapping("/user")
    public Principal user(Principal user) {
        return user;
    }

}
