package com.business.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;

/**
 * Created by AlexBob on 2015-03-24.
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@Configuration
public class AuthenticationSecurity extends  GlobalAuthenticationConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        // @formatter:off
        auth
                .jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(bcryptEncoder())
                .getUserDetailsService().setEnableGroups(true);

        // @formatter:on
    }

    @Bean
    public JdbcUserDetailsManager jdbcUserDetailsManager(){
        JdbcUserDetailsManager jdm= new JdbcUserDetailsManager();
        jdm.setDataSource(dataSource);
        jdm.setEnableGroups(true);
        return jdm;
    }

    @Bean
    public  BCryptPasswordEncoder bcryptEncoder()
    {
        return new BCryptPasswordEncoder();
    }

}
