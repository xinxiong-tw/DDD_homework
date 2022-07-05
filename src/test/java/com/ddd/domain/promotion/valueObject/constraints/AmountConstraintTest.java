package com.ddd.domain.promotion.valueObject.constraints;

import com.ddd.domain.calculation.valueObject.TransactionContext;
import com.ddd.domain.promotion.valueObject.ProductSet;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertTrue;

class AmountConstraintTest {

    @Test
    void should_pass_if_total_amount_is_200_and_min_amount_is_100() {
        ProductSet stubProductSet = Mockito.mock(ProductSet.class);
        TransactionContext stubTransactionContext = Mockito.mock(TransactionContext.class);
        AmountConstraint amountConstraint = new AmountConstraint(new BigDecimal(200), null, stubProductSet);
        Mockito.when(stubProductSet.include(Mockito.any())).thenReturn(true);

        assertTrue(amountConstraint.isSatisfied(stubTransactionContext));
    }

}