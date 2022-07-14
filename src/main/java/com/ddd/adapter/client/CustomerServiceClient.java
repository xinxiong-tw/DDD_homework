package com.ddd.adapter.client;

import com.ddd.domain.calculation.valueObject.CustomerInfo;
import com.ddd.domain.calculation.valueObject.CustomerRole;

public class CustomerServiceClient implements com.ddd.domain.calculation.outbound.CustomerService {
    @Override
    public CustomerInfo getCustomerInfoById(String customerId) {
        return new CustomerInfo(customerId, new CustomerRole("role"));
    }
}
