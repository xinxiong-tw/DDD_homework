package com.ddd.domain.promotion.valueObject.constraints;

import com.ddd.domain.calculation.valueObject.TransactionContext;
import com.ddd.domain.promotion.enums.Operator;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ComposedConstraintTest {

    @Test
    void should_pass_if_all_constraints_pass() {
        PromotionConstraint stubConstraint1 = Mockito.mock(PromotionConstraint.class);
        PromotionConstraint stubConstraint2 = Mockito.mock(PromotionConstraint.class);
        PromotionConstraint stubConstraint3 = Mockito.mock(PromotionConstraint.class);
        TransactionContext dummyTransactionContext = Mockito.mock(TransactionContext.class);
        ComposedConstraint composedConstraint = new ComposedConstraint(List.of(stubConstraint1, stubConstraint2, stubConstraint3), Operator.AND);

        Mockito.when(stubConstraint1.isSatisfied(dummyTransactionContext)).thenReturn(true);
        Mockito.when(stubConstraint2.isSatisfied(dummyTransactionContext)).thenReturn(true);
        Mockito.when(stubConstraint3.isSatisfied(dummyTransactionContext)).thenReturn(true);

        assertTrue(composedConstraint.isSatisfied(dummyTransactionContext));
    }


    @Test
    void should_fail_if_not_all_constraints_pass() {
        PromotionConstraint stubConstraint1 = Mockito.mock(PromotionConstraint.class);
        PromotionConstraint stubConstraint2 = Mockito.mock(PromotionConstraint.class);
        PromotionConstraint stubConstraint3 = Mockito.mock(PromotionConstraint.class);
        TransactionContext dummyTransactionContext = Mockito.mock(TransactionContext.class);
        ComposedConstraint composedConstraint = new ComposedConstraint(List.of(stubConstraint1, stubConstraint2, stubConstraint3), Operator.AND);

        Mockito.when(stubConstraint1.isSatisfied(dummyTransactionContext)).thenReturn(false);
        Mockito.when(stubConstraint2.isSatisfied(dummyTransactionContext)).thenReturn(true);
        Mockito.when(stubConstraint3.isSatisfied(dummyTransactionContext)).thenReturn(true);

        assertFalse(composedConstraint.isSatisfied(dummyTransactionContext));
    }

    @Test
    void should_pass_if_any_constraints_pass() {
        PromotionConstraint stubConstraint1 = Mockito.mock(PromotionConstraint.class);
        PromotionConstraint stubConstraint2 = Mockito.mock(PromotionConstraint.class);
        PromotionConstraint stubConstraint3 = Mockito.mock(PromotionConstraint.class);
        TransactionContext dummyTransactionContext = Mockito.mock(TransactionContext.class);
        ComposedConstraint composedConstraint = new ComposedConstraint(List.of(stubConstraint1, stubConstraint2, stubConstraint3), Operator.OR);

        Mockito.when(stubConstraint1.isSatisfied(dummyTransactionContext)).thenReturn(false);
        Mockito.when(stubConstraint2.isSatisfied(dummyTransactionContext)).thenReturn(true);
        Mockito.when(stubConstraint3.isSatisfied(dummyTransactionContext)).thenReturn(true);

        assertTrue(composedConstraint.isSatisfied(dummyTransactionContext));
    }

    @Test
    void should_fail_if_all_constraints_fail() {
        PromotionConstraint stubConstraint1 = Mockito.mock(PromotionConstraint.class);
        PromotionConstraint stubConstraint2 = Mockito.mock(PromotionConstraint.class);
        PromotionConstraint stubConstraint3 = Mockito.mock(PromotionConstraint.class);
        TransactionContext dummyTransactionContext = Mockito.mock(TransactionContext.class);
        ComposedConstraint composedConstraint = new ComposedConstraint(List.of(stubConstraint1, stubConstraint2, stubConstraint3), Operator.OR);

        Mockito.when(stubConstraint1.isSatisfied(dummyTransactionContext)).thenReturn(false);
        Mockito.when(stubConstraint2.isSatisfied(dummyTransactionContext)).thenReturn(false);
        Mockito.when(stubConstraint3.isSatisfied(dummyTransactionContext)).thenReturn(false);

        assertFalse(composedConstraint.isSatisfied(dummyTransactionContext));
    }

}