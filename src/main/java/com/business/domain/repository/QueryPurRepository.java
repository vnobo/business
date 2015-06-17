package com.business.domain.repository;

import com.business.domain.QueryPurItem;
import com.business.domain.QueryPurStatistic;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by billb on 2015-05-04.
 */
@RepositoryRestResource(path = "querypurrest",collectionResourceRel = "queryPurs",itemResourceRel = "queryPur")
public interface QueryPurRepository extends PagingAndSortingRepository<QueryPurItem, Long>,JpaSpecificationExecutor<QueryPurItem>,QueryPurRepositoryCustom {
}