package com.ddd.domain.calculation.entity;

import com.ddd.domain.calculation.valueObject.CustomerRole;
public class CustomerInfo {
    private String id;
    private CustomerRole customerRole;

    public CustomerInfo(String id, CustomerRole customerRole) {
        this.id = id;
        this.customerRole = customerRole;
    }

    public CustomerRole getCustomerRole() {
        return this.customerRole;
    }
}
