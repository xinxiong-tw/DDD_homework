package com.ddd.domain.promotion.valueObject.rule;

import com.ddd.domain.calculation.valueObject.TransactionContext;

public abstract class PromotionRule {
    public abstract TransactionContext applyRule(TransactionContext transactionContext);
}
