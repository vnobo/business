package com.business.domain.repository;

import com.business.domain.Goods;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

/**
 * Created by billb on 2015-04-23.
 */
@RepositoryRestResource(path = "goodsrest", collectionResourceRel = "goodses", itemResourceRel = "goods")
public interface GoodsRepository extends PagingAndSortingRepository<Goods, Integer> {

    @RestResource(path = "namect")
    Page<Goods> findByNameContaining(@Param("name") String name, Pageable pageable);

    @RestResource(path = "goodsidct")
    Page<Goods> findByGoodsid(@Param("id") int id, Pageable pageable);
}
