package com.business.domain.repository;

import com.business.domain.Purchase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by billb on 2015-04-15.
 */
@RepositoryRestResource(path = "purchaserest")
public interface PurchaseRepository extends PagingAndSortingRepository<Purchase, String>, JpaSpecificationExecutor {

    Page<Purchase> findByUsername(@Param("username") String name, Pageable pageable);

    Page<Purchase> findByFlag(@Param("flag") int flag, Pageable pageable);

    Page<Purchase> findByFlagGreaterThan(@Param("flag") int flag, Pageable pageable);


    Page<Purchase> findAll(Specification spec, Pageable pageable);

}
