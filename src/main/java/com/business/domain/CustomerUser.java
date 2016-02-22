package com.business.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * Created by billb on 2015-05-27.
 */
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
public class CustomerUser {

    @Id
    private String username;

    @JsonIgnore
    @Column(updatable = false)
    private String password;
    @Column(updatable = false)
    private boolean enabled;

    public CustomerUser() {

    }

    public CustomerUser(String username, String password, boolean enabled) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
    }

    public String getUName() {
        return username;
    }

    public void setUName(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isEnabled() {
        return enabled;
    }
}
