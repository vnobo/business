package com.business.service;

import com.business.domain.RoleMenu;
import com.business.domain.repository.RoleMenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Created by billb on 2015-03-28.
 */

@Service
public class RoleMenuServiceImpl implements RoleMenuService {


    private RoleMenuRepository menuRepository;


    @Autowired
    public RoleMenuServiceImpl(RoleMenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }


    /**
     * 获取当前用户配置权限菜单
     *
     * @return 当前登录的用户权限表
     */
    public List<RoleMenu> loadMenuByDefaultAll() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<RoleMenu> menus = new ArrayList<>();
        if (principal instanceof UserDetails) {
            Collection<? extends GrantedAuthority> collections = ((UserDetails) principal).getAuthorities();
            List<String> auth = collections.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
            menuRepository.findByAuthorityIn(auth).parallelStream()
                    .filter(p -> p.getSubMenus().stream()
                            .filter(s -> auth.contains(s.getAuthority())).count() > 0)
                    .forEach(p -> {
                        p.setSubMenus(p.getSubMenus().stream()
                                .filter(m -> auth.contains(m.getAuthority()))
                                .collect(Collectors.toList()));
                        menus.add(p);
                    });

            if (menus.size() == 0) {
                return menuRepository.findByAuthorityIn(auth);
            }

        }
        return menus;
    }

    public List<RoleMenu> findAllMenus() {

        return menuRepository.findAll().parallelStream().filter(m -> m.getSubMenus().size() > 0).distinct().collect(Collectors.toList());
    }
}
