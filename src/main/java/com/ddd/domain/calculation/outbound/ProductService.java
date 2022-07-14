package com.ddd.domain.calculation.outbound;

import com.ddd.domain.calculation.valueObject.PriceTable;

import java.util.List;

public interface ProductService {
    PriceTable getPriceTableByIds(List<String> productIds);
}
