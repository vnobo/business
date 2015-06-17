package com.business.service;


import com.business.domain.Customer;
import com.business.domain.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

/**
 * Created by billb on 2015-03-25.
 */
@Service
public class CustomerServiceImpl extends JdbcUserDetailsManager implements CustomerService {


    private CustomerRepository repository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository userInfoRepository, DataSource dataSource) {
        this.repository = userInfoRepository;
        setDataSource(dataSource);
    }

    public Customer loadCustomerByUsername(String username) {
        return repository.findOne(username);
    }

    public void saveCustomer(Customer customer) {
        repository.save(customer);
    }

    public void deleteCustomer(String username){
        getJdbcTemplate().update("delete from customers where username=?",username);
        removeUserFromGroup(username);
    }

    public void removeUserFromGroup(String username){
        getJdbcTemplate().update("delete from group_members where username=?",username);
    }

}
