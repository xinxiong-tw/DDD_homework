package com.ddd.domain.promotion.valueObject.constraints;

import com.ddd.domain.calculation.entity.PricedTransactionItem;
import com.ddd.domain.calculation.valueObject.TransactionContext;
import com.ddd.domain.promotion.valueObject.ProductSet;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AmountConstraintTest {

    @Test
    void should_pass_if_total_amount_is_200_and_min_amount_is_100() {
        ProductSet stubProductSet = Mockito.mock(ProductSet.class);
        TransactionContext stubTransactionContext = Mockito.mock(TransactionContext.class);
        AmountConstraint amountConstraint = new AmountConstraint(new BigDecimal(100), null, stubProductSet);
        Mockito.when(stubProductSet.include(Mockito.any())).thenReturn(true);
        Mockito.when(stubTransactionContext.getItems()).thenReturn(List.of(
                PricedTransactionItem.builder().id("1").price(new BigDecimal(100)).count(1).build(),
                PricedTransactionItem.builder().id("2").price(new BigDecimal(50)).count(2).build()
        ));

        assertTrue(amountConstraint.isSatisfied(stubTransactionContext));
    }

    @Test
    void should_fail_if_total_amount_is_200_and_min_amount_is_300() {
        ProductSet stubProductSet = Mockito.mock(ProductSet.class);
        TransactionContext stubTransactionContext = Mockito.mock(TransactionContext.class);
        AmountConstraint amountConstraint = new AmountConstraint(new BigDecimal(300), null, stubProductSet);
        Mockito.when(stubProductSet.include(Mockito.any())).thenReturn(true);
        Mockito.when(stubTransactionContext.getItems()).thenReturn(List.of(
                PricedTransactionItem.builder().id("1").price(new BigDecimal(50)).count(2).build(),
                PricedTransactionItem.builder().id("2").price(new BigDecimal(50)).count(2).build()
        ));

        assertFalse(amountConstraint.isSatisfied(stubTransactionContext));
    }

}