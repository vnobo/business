package com.business.domain.repository;

import com.business.domain.RoleMenu;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by billb on 2015-03-28.
 */
public interface RoleMenuRepository extends CrudRepository<RoleMenu, Integer> {

    List<RoleMenu> findAll();
    List<RoleMenu> findByAuthorityIn(List<String> authoritys);

}
