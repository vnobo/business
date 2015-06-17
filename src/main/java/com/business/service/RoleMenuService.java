package com.business.service;

import com.business.domain.RoleMenuDetails;

import java.util.Collection;

/**
 * Created by billb on 2015-03-28.
 */

public interface RoleMenuService {


    /**
     * 获取当前用户配置权限菜单
     * @return 当前登录的用户权限表
     **/
    Collection<? extends RoleMenuDetails> loadMenuByDefaultAll();

    /**
     * 获取当前所有菜单
     * @return
     */
    Collection<? extends RoleMenuDetails> findAllMenus();
}
