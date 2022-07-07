package com.ddd.domain.promotion.valueObject.rule;

import com.ddd.domain.calculation.valueObject.TransactionContext;

public interface PromotionRule {
    TransactionContext applyRule(TransactionContext transactionContext);
}
