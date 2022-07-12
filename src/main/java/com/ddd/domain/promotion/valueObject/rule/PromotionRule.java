package com.ddd.domain.promotion.valueObject.rule;

import com.ddd.domain.calculation.valueObject.TransactionContext;
import com.ddd.domain.promotion.entity.Promotion;

public interface PromotionRule {
    TransactionContext applyRule(TransactionContext transactionContext, Promotion promotion);
}
