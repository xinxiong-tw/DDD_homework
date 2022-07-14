package com.ddd.adapter.client;

import com.ddd.domain.calculation.outbound.ProductService;
import com.ddd.domain.calculation.valueObject.PriceTable;

import java.util.List;

public class ProductServiceClient implements ProductService {
    @Override
    public PriceTable getPriceTableByIds(List<String> productIds) {
        return null;
    }
}
