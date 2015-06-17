package com.business.domain;

import java.io.Serializable;
import java.util.List;

/**
 * Created by billb on 2015-04-01.
 */
public interface RoleMenuDetails extends Serializable {

    int getId();

    String getMenuName();

    String getUrl();

    String getGroupName();

    int getOrderId();

    String getAuthority();

    List<? extends RoleMenuDetails> getSubsetMenus();



}
