package com.business.domain.repository;

import com.business.domain.OrderGoodsLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import java.sql.Timestamp;

/**
 * Created by billb on 2015-05-05.
 */
@RepositoryRestResource(path = "ordergoodslogrest")
@PreAuthorize("hasRole('ROLE_ADMIN_REPGOODSLOG')")
public interface OrderGoodsLogRepository extends PagingAndSortingRepository<OrderGoodsLog, Long> {

    @RestResource(path = "findbydate")
    Page<OrderGoodsLog> findByLastDateTimeBetweenOrderByLastDateTimeDesc(@Param("start") Timestamp startDate, @Param("end") Timestamp endDate, Pageable pageable);

    @RestResource(path = "findbyides")
    Page<OrderGoodsLog> findByLastDateTimeBetweenAndParamGoodsGoodsidOrderByLastDateTimeDesc(@Param("start") Timestamp startDate,
                                                                                             @Param("end") Timestamp endDate,
                                                                                             @Param("id") int indes,
                                                                                             Pageable pageable);

    @RestResource(path = "findbynames")
    Page<OrderGoodsLog> findByLastDateTimeBetweenAndParamGoodsNameContainingOrderByLastDateTimeDesc(@Param("start") Timestamp startDate,
                                                                                                    @Param("end") Timestamp endDate,
                                                                                                    @Param("name") String names,
                                                                                                    Pageable pageable);
}
