package com.ddd.domain.calculation.valueObject;

public class CustomerInfo {
    private final String id;
    private final CustomerRole customerRole;

    public CustomerInfo(String id, CustomerRole customerRole) {
        this.id = id;
        this.customerRole = customerRole;
    }

    public CustomerRole getCustomerRole() {
        return this.customerRole;
    }
}
