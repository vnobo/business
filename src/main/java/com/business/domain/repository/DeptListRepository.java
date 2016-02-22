package com.business.domain.repository;

import com.business.domain.DeptList;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by billb on 2015-04-26.
 */
@RepositoryRestResource(path = "deptrest")
public interface DeptListRepository extends PagingAndSortingRepository<DeptList, Integer> {
}
