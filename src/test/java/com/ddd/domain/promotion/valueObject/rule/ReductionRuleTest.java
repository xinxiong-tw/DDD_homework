package com.ddd.domain.promotion.valueObject.rule;

import com.ddd.domain.calculation.entity.PricedTransactionItem;
import com.ddd.domain.calculation.valueObject.TransactionContext;
import com.ddd.domain.promotion.entity.Promotion;
import com.ddd.domain.promotion.valueObject.Amount;
import com.ddd.domain.promotion.valueObject.productSet.ListProductSet;
import com.ddd.domain.promotion.valueObject.productSet.AllProductSet;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;

class ReductionRuleTest {
    @Test
    void should_reduce_50_yuan_for_all_products() {
        ReductionRule reductionRule = new ReductionRule(
                Amount.builder()
                        .discountAmount(new BigDecimal(50))
                        .build(),
                new AllProductSet()
        );
        TransactionContext stubTransactionContext = Mockito.mock(TransactionContext.class);

        Mockito.when(stubTransactionContext.getItems()).thenReturn(List.of(
                PricedTransactionItem.builder().id("1").price(new BigDecimal(100)).count(1).build(),
                PricedTransactionItem.builder().id("2").price(new BigDecimal(80)).count(2).build()
        ));

        TransactionContext appliedTransactionContext = reductionRule.applyRule(stubTransactionContext, Mockito.mock(Promotion.class));

        Mockito.verify(appliedTransactionContext).addNextPricedTransactionItems(List.of(
                PricedTransactionItem.builder().id("1").price(new BigDecimal(50)).count(1).build(),
                PricedTransactionItem.builder().id("2").price(new BigDecimal(30)).count(2).build()
        ));
    }

    @Test
    void should_reduce_50_yuan_for_selected_products() {
        ReductionRule reductionRule = new ReductionRule(Amount.builder()
                .discountAmount(new BigDecimal(50))
                .build(),
                new ListProductSet(List.of("1"))
        );
        TransactionContext stubTransactionContext = Mockito.mock(TransactionContext.class);

        Mockito.when(stubTransactionContext.getItems()).thenReturn(List.of(
                PricedTransactionItem.builder().id("1").price(new BigDecimal(100)).count(1).build(),
                PricedTransactionItem.builder().id("2").price(new BigDecimal(80)).count(2).build()
        ));

        TransactionContext appliedTransactionContext = reductionRule.applyRule(stubTransactionContext, Mockito.mock(Promotion.class));

        Mockito.verify(appliedTransactionContext).addNextPricedTransactionItems(List.of(
                PricedTransactionItem.builder().id("1").price(new BigDecimal(50)).count(1).build(),
                PricedTransactionItem.builder().id("2").price(new BigDecimal(80)).count(2).build()
        ));
    }

    @Test
    void should_reduce_to_0_at_most_for_selected_products() {
        ReductionRule reductionRule = new ReductionRule(Amount.builder()
                .discountAmount(new BigDecimal(200))
                .build(),
                new ListProductSet(List.of("1"))
        );
        TransactionContext stubTransactionContext = Mockito.mock(TransactionContext.class);

        Mockito.when(stubTransactionContext.getItems()).thenReturn(List.of(
                PricedTransactionItem.builder().id("1").price(new BigDecimal(100)).count(1).build(),
                PricedTransactionItem.builder().id("2").price(new BigDecimal(80)).count(2).build()
        ));

        TransactionContext appliedTransactionContext = reductionRule.applyRule(stubTransactionContext, Mockito.mock(Promotion.class));

        Mockito.verify(appliedTransactionContext).addNextPricedTransactionItems(List.of(
                PricedTransactionItem.builder().id("1").price(new BigDecimal(0)).count(1).build(),
                PricedTransactionItem.builder().id("2").price(new BigDecimal(80)).count(2).build()
        ));
    }

}