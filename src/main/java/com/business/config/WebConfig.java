package com.business.config;

import com.jolbox.bonecp.BoneCPDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.webjars.WebJarAssetLocator;

import javax.sql.DataSource;


/**
 * Created by Bob on 2015/2/2.
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.driver-class-name}")
    private String classname;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;


    @Bean
    public DataSource dataSource() {
        BoneCPDataSource boneCPDataSource = new BoneCPDataSource();
        boneCPDataSource.setDriverClass(classname);
        boneCPDataSource.setJdbcUrl(url);
        boneCPDataSource.setUser(username);
        boneCPDataSource.setPassword(password);
        return boneCPDataSource;
    }

    @Bean
    public WebJarAssetLocator webJarAssetLocator() {
        return new WebJarAssetLocator();
    }


}
