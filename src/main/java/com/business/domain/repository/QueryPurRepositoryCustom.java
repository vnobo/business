package com.business.domain.repository;

import com.business.domain.QueryPurStatistic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Map;

/**
 * Created by billb on 2015/6/4.
 */
public interface QueryPurRepositoryCustom {

    Page<QueryPurStatistic> findAllGroupBy(Map<String, Object> params, Pageable page);
}
