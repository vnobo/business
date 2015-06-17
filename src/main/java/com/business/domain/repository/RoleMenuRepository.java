package com.business.domain.repository;

import com.business.domain.RoleMenu;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Created by billb on 2015-03-28.
 */
@RepositoryRestResource(exported = false)
public interface RoleMenuRepository extends PagingAndSortingRepository<RoleMenu, Integer> {

    List<RoleMenu> findByGroupName(String groupName);

    List<RoleMenu> findAll();
}
