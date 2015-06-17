package com.business.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.time.Instant;

/**
 * Created by billb on 2015-05-28.
 */
@Entity
@Table(name = "purchase")
public class QueryPur {

    @Id
    private String sheetid;

    @Column(name = "Editor", nullable = false)
    private String username;
    private Timestamp editdate = Timestamp.from(Instant.now());
    private String hander;
    private Timestamp handdate;
    private String checker;
    private Timestamp checkdate = Timestamp.from(Instant.now());
    private int flag;
    private String note;


    public String getUsername() {
        return username;
    }

    public Timestamp getEditdate() {
        return editdate;
    }

    public String getHander() {
        return hander;
    }

    public Timestamp getHanddate() {
        return handdate;
    }

    public String getChecker() {
        return checker;
    }

    public Timestamp getCheckdate() {
        return checkdate;
    }

    public int getFlag() {
        return flag;
    }

    public String getNote() {
        return note;
    }

    public String getSheetid() {
        return sheetid;
    }
    public String getId() {
        return sheetid;
    }
}
