package com.business.service;

import com.business.domain.Customer;
import org.springframework.security.provisioning.GroupManager;
import org.springframework.security.provisioning.UserDetailsManager;

/**
 * Created by billb on 2015-03-25.
 */

public interface CustomerService extends UserDetailsManager, GroupManager {

    Customer loadCustomerByUsername(String username);

    void saveCustomer(Customer customer);

    void deleteCustomer(String username);
}
