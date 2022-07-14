package com.ddd.domain.calculation.service;

import com.ddd.domain.promotion.entity.Promotion;
import com.ddd.domain.calculation.valueObject.TransactionContext;

import java.util.Optional;

public class TransactionCalculator {
    public static TransactionContext calculate(TransactionContext transactionContext, Promotion promotion) {
        return Optional.of(promotion)
                .filter(it -> promotion.getPromotionConstraint().isSatisfied(transactionContext))
                .map(Promotion::getRule)
                .map(it -> it.applyRule(transactionContext, promotion))
                .map(transactionContext::addTransactionContext)
                .orElse(transactionContext);
    }
}
