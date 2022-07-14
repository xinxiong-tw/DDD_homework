package com.ddd.adapter.client;

import com.ddd.domain.calculation.outbound.CustomerService;
import com.ddd.domain.calculation.valueObject.CustomerInfo;
import com.ddd.domain.calculation.valueObject.CustomerRole;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceClient implements CustomerService {
    @Override
    public CustomerInfo getCustomerInfoById(String customerId) {
        return new CustomerInfo(customerId, new CustomerRole("role"));
    }
}
