package com.business.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerMapping;

import org.webjars.WebJarAssetLocator;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLConnection;
import java.security.Principal;

/**
 * Created by billb on 2015-03-26.
 */

public class BaseController {

    @Autowired
    private WebJarAssetLocator assetLocator;

    @RequestMapping("/user")
    public Principal user(Principal user) {
        return user;
    }


    @RequestMapping("/webjarslocator/{webjar}/**")
    public ResponseEntity<Resource> locateWebjarAsset(@PathVariable String webjar, HttpServletRequest request) {
        try {
            String mvcPrefix = "/webjarslocator/" + webjar + "/"; // This prefix must match the mapping path!
            String mvcPath = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
            String fullPath = assetLocator.getFullPath(webjar, mvcPath.substring(mvcPrefix.length()));
            ClassPathResource res = new ClassPathResource(fullPath);
            return new ResponseEntity<>(res, addAttrsToHeader(new HttpHeaders(), res), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Extracts the {@code content-length}, {@code last-modified} and {@code expires} attributes from
     * a resource and puts them into HTTP Headers.
     *
     * @param headers
     * @param resource
     * @return
     * @throws IOException
     */
    private static HttpHeaders addAttrsToHeader(HttpHeaders headers, Resource resource) throws IOException {
        URLConnection conn = resource.getURL().openConnection();
        if (conn.getContentLengthLong() != -1) {
            headers.setContentLength(conn.getContentLengthLong());
        }
        if (conn.getLastModified() != 0) {
            headers.setLastModified(conn.getLastModified());
        }
        if (conn.getExpiration() != 0) {
            headers.setExpires(conn.getExpiration());
        }
        return headers;
    }

}
