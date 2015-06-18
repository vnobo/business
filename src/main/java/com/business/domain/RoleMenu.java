package com.business.domain;


import javax.persistence.*;
import java.util.List;

/**
 * Created by billb on 2015-03-28.
 */
@Entity(name = "roles_menus")
public class RoleMenu{

    @Id
    private int id;

    @Column(nullable = false)
    private String menuName;

    private String url;

    @Column(nullable = false)
    private String groupName;

    private int orderId;


    private String authority;

    @OneToMany
    @JoinColumn(name = "parent_id")
    private List<RoleMenu> subRoleMenus;

    public int getId() {
        return id;
    }


    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }


    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }


    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public List<RoleMenu> getSubMenus() {
        return subRoleMenus;
    }

    public void setSubMenus(List<RoleMenu> subsetMenus) {
        this.subRoleMenus = subsetMenus;
    }
}
