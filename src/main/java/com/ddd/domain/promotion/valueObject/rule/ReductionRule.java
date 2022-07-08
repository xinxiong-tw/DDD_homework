package com.ddd.domain.promotion.valueObject.rule;

import com.ddd.domain.calculation.valueObject.TransactionContext;
import com.ddd.domain.promotion.valueObject.productSet.ProductSet;
import com.ddd.domain.promotion.valueObject.Amount;
import lombok.Builder;

@Builder
public class ReductionRule implements PromotionRule {
    private Amount reduceAmount;
    private ProductSet reducibleProductSet;

    @Override
    public TransactionContext applyRule(TransactionContext transactionContext) {
        return null;
    }
}
