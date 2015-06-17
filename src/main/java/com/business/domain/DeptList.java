package com.business.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by billb on 2015-04-24.
 */
@Entity
@Table(name = "dept_list")
public class DeptList {

    @Id
    private int id;
    private String name;

    public DeptList(){
        this.id=999999;
    }
    public int getDeptId() {
        return id;
    }
    public void setDeptId(int id) {this.id=id;}
    public String getName() {
        return name;
    }

}
