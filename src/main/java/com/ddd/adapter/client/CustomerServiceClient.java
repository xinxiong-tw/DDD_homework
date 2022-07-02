package com.ddd.adapter.client;

import com.ddd.domain.calculation.entity.CustomerInfo;
import com.ddd.domain.calculation.valueObject.CustomerRole;

public class CustomerServiceClient {
    public static CustomerInfo getCustomerInfoById(String customerId) {
        return new CustomerInfo(customerId, new CustomerRole("role"));
    }
}
