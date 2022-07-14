package com.ddd.adapter.client;

import com.ddd.domain.calculation.outbound.ProductService;
import com.ddd.domain.calculation.valueObject.PriceTable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceClient implements ProductService {
    @Override
    public PriceTable getPriceTableByIds(List<String> productIds) {
        return null;
    }
}
