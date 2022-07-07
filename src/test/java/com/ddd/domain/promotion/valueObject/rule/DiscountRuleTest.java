package com.ddd.domain.promotion.valueObject.rule;

import com.ddd.domain.calculation.entity.PricedTransactionItem;
import com.ddd.domain.calculation.valueObject.TransactionContext;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DiscountRuleTest {

    @Test
    void should_discount_all_products_by_20_percent() {
        DiscountRule discountRule = new DiscountRule(new BigDecimal("0.2"));
        TransactionContext stubTransactionContext = Mockito.mock(TransactionContext.class);

        Mockito.when(stubTransactionContext.getItems()).thenReturn(List.of(
                PricedTransactionItem.builder().id("1").price(new BigDecimal(100)).count(1).build(),
                PricedTransactionItem.builder().id("2").price(new BigDecimal(80)).count(2).build()
        ));

        TransactionContext appliedTransactionContext = discountRule.applyRule(stubTransactionContext);

        assertEquals(appliedTransactionContext.getItems(), List.of(
                PricedTransactionItem.builder().id("1").price(new BigDecimal(80)).count(1).build(),
                PricedTransactionItem.builder().id("2").price(new BigDecimal(64)).count(2).build()
        ));
    }

}