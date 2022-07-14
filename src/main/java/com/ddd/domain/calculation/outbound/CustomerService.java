package com.ddd.domain.calculation.outbound;

import com.ddd.domain.calculation.valueObject.CustomerInfo;

public interface CustomerService {
    CustomerInfo getCustomerInfoById(String customerId);
}
