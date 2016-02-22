package com.business.domain.repository;

import com.business.domain.OrderGoods;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

/**
 * Created by billb on 2015-04-24.
 */
@RepositoryRestResource(path = "ordergoodsrest")
public interface OrderGoodsRepository extends PagingAndSortingRepository<OrderGoods, Integer> {

    @RestResource(path = "namect")
    Page<OrderGoods> findByParamGoodsNameContaining(@Param("name") String name, Pageable pageable);

    @RestResource(path = "goodsidct")
    Page<OrderGoods> findByGoodsid(@Param("id") int id, Pageable pageable);

    @RestResource(path = "deptandpricenot")
    Page<OrderGoods> findByParamDeptIdNotAndPriceNot(@Param("dept") int deptid, @Param("price") double price, Pageable pageable);

    @RestResource(path = "findbydept")
    Page<OrderGoods> findByParamDeptId(@Param("id") int id, Pageable pageable);

    @RestResource(path = "findByIdOrName")
    Page<OrderGoods> findByGoodsidOrParamGoodsNameContaining(@Param("id") int id, @Param("name") String name, Pageable pageable);

    OrderGoods findByGoodsid(int goodsid);

    List<OrderGoods> findByParamDeptId(int deptid);

}
