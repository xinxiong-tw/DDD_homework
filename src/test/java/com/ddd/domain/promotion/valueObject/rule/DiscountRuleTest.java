package com.ddd.domain.promotion.valueObject.rule;

import com.ddd.domain.calculation.entity.PricedTransactionItem;
import com.ddd.domain.calculation.valueObject.TransactionContext;
import com.ddd.domain.promotion.valueObject.productSet.ListProductSet;
import com.ddd.domain.promotion.valueObject.productSet.NullProductSet;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;

class DiscountRuleTest {

    @Test
    void should_discount_all_products_by_20_percent() {
        DiscountRule discountRule = new DiscountRule(new BigDecimal("0.2"), new NullProductSet());
        TransactionContext stubTransactionContext = Mockito.mock(TransactionContext.class);

        Mockito.when(stubTransactionContext.getItems()).thenReturn(List.of(
                PricedTransactionItem.builder().id("1").price(new BigDecimal(100)).count(1).build(),
                PricedTransactionItem.builder().id("2").price(new BigDecimal(80)).count(2).build()
        ));

        TransactionContext appliedTransactionContext = discountRule.applyRule(stubTransactionContext);

        Mockito.verify(appliedTransactionContext).addNextPricedTransactionItems(List.of(
                PricedTransactionItem.builder().id("1").price(new BigDecimal(80)).count(1).build(),
                PricedTransactionItem.builder().id("2").price(new BigDecimal(64)).count(2).build()
        ));
    }

    @Test
    void should_discount_some_products_by_20_percent() {
        DiscountRule discountRule = new DiscountRule(new BigDecimal("0.2"), new ListProductSet(List.of("1")));
        TransactionContext stubTransactionContext = Mockito.mock(TransactionContext.class);

        Mockito.when(stubTransactionContext.getItems()).thenReturn(List.of(
                PricedTransactionItem.builder().id("1").price(new BigDecimal(100)).count(1).build(),
                PricedTransactionItem.builder().id("2").price(new BigDecimal(80)).count(2).build()
        ));

        TransactionContext appliedTransactionContext = discountRule.applyRule(stubTransactionContext);

        Mockito.verify(appliedTransactionContext).addNextPricedTransactionItems(List.of(
                PricedTransactionItem.builder().id("1").price(new BigDecimal(80)).count(1).build(),
                PricedTransactionItem.builder().id("2").price(new BigDecimal(80)).count(2).build()
        ));
    }

    @Test
    void should_discount_max_to_100_percent() {
        DiscountRule discountRule = new DiscountRule(new BigDecimal("1.5"), new ListProductSet(List.of("1")));
        TransactionContext stubTransactionContext = Mockito.mock(TransactionContext.class);

        Mockito.when(stubTransactionContext.getItems()).thenReturn(List.of(
                PricedTransactionItem.builder().id("1").price(new BigDecimal(100)).count(1).build(),
                PricedTransactionItem.builder().id("2").price(new BigDecimal(80)).count(2).build()
        ));

        TransactionContext appliedTransactionContext = discountRule.applyRule(stubTransactionContext);

        Mockito.verify(appliedTransactionContext).addNextPricedTransactionItems(List.of(
                PricedTransactionItem.builder().id("1").price(new BigDecimal(0)).count(1).build(),
                PricedTransactionItem.builder().id("2").price(new BigDecimal(80)).count(2).build()
        ));
    }

}