package com.business.domain.repository;


import com.business.domain.Customer;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;


/**
 * Created by AlexBob on 2015-03-23.
 */
@RepositoryRestResource(path = "customerRest")
public interface CustomerRepository extends PagingAndSortingRepository<Customer,String>{

}
