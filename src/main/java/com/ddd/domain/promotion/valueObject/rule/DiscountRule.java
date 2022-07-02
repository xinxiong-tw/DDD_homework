package com.ddd.domain.promotion.valueObject.rule;

import com.ddd.domain.calculation.valueObject.TransactionContext;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public class DiscountRule extends PromotionRule {
    private BigDecimal discountRate;

    @Override
    public TransactionContext applyRule(TransactionContext transactionContext) {
        return null;
    }
}
