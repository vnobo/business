package com.business.service;

import com.business.domain.QueryPurItem;
import com.business.domain.QueryPurStatistic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * Created by billb on 2015-05-12.
 */
public interface QueryPurService {

    List<QueryPurItem> findAllPurToday(Map<String, Object> params);

    Page<QueryPurItem> findAllParams(Map<String,Object> params,Pageable page);

    Page<QueryPurStatistic> findPurParamsGroupBy(Map<String, Object> params, Pageable page);
}
