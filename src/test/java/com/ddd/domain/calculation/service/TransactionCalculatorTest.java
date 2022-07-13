package com.ddd.domain.calculation.service;

import com.ddd.domain.calculation.valueObject.TransactionContext;
import com.ddd.domain.promotion.entity.Promotion;
import com.ddd.domain.promotion.valueObject.constraints.PromotionConstraint;
import com.ddd.domain.promotion.valueObject.rule.PromotionRule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class TransactionCalculatorTest {

    @Test
    void should_return_same_context_if_not_pass_constraint() {
        Promotion stubPromotion = Mockito.mock(Promotion.class);
        PromotionConstraint stubPromotionConstraint = Mockito.mock(PromotionConstraint.class);
        TransactionContext dummyTransaction = Mockito.mock(TransactionContext.class);
        Mockito.when(stubPromotion.getPromotionConstraint()).thenReturn(stubPromotionConstraint);
        Mockito.when(stubPromotionConstraint.isSatisfied(dummyTransaction)).thenReturn(false);

        Assertions.assertEquals(dummyTransaction, TransactionCalculator.calculate(dummyTransaction, stubPromotion));
    }

    @Test
    void should_call_apply_if_pass_return_same_context() {
        Promotion stubPromotion = Mockito.mock(Promotion.class);
        PromotionConstraint stubPromotionConstraint = Mockito.mock(PromotionConstraint.class);
        TransactionContext dummyTransaction = Mockito.mock(TransactionContext.class);
        PromotionRule spyPromotionRule = Mockito.mock(PromotionRule.class);

        Mockito.when(stubPromotion.getPromotionConstraint()).thenReturn(stubPromotionConstraint);
        Mockito.when(stubPromotion.getRule()).thenReturn(spyPromotionRule);
        Mockito.when(stubPromotionConstraint.isSatisfied(dummyTransaction)).thenReturn(true);

        TransactionCalculator.calculate(dummyTransaction, stubPromotion);

        Mockito.verify(spyPromotionRule).applyRule(dummyTransaction, stubPromotion);
    }

}