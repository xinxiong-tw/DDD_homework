package com.ddd.domain.promotion.valueObject.rule;

import com.ddd.domain.calculation.valueObject.AppliedPromotionInfo;
import com.ddd.domain.calculation.valueObject.PricedTransactionItem;
import com.ddd.domain.calculation.valueObject.PromotionInfo;
import com.ddd.domain.calculation.valueObject.TransactionContext;
import com.ddd.domain.promotion.entity.Promotion;
import com.ddd.domain.promotion.valueObject.Amount;
import com.ddd.domain.promotion.valueObject.productSet.AllProductSet;
import com.ddd.domain.promotion.valueObject.productSet.ListProductSet;
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

        Promotion stubPromotion = Mockito.mock(Promotion.class);
        Mockito.when(stubPromotion.getId()).thenReturn(1L);
        Mockito.when(stubPromotion.getPromotionInfo()).thenReturn(PromotionInfo.builder().title("title").description("description").build());

        reductionRule.applyRule(stubTransactionContext, stubPromotion);

        Mockito.verify(stubTransactionContext).createNextTransactionContext(List.of(
                PricedTransactionItem.builder()
                        .id("1").price(new BigDecimal(50)).count(1)
                        .appliedPromotionInfo(new AppliedPromotionInfo(stubPromotion.getId(), stubPromotion.getPromotionInfo(), new BigDecimal(50))).build(),
                PricedTransactionItem.builder()
                        .id("2").price(new BigDecimal(30)).count(2)
                        .appliedPromotionInfo(new AppliedPromotionInfo(stubPromotion.getId(), stubPromotion.getPromotionInfo(), new BigDecimal(50))).build()
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

        Promotion stubPromotion = Mockito.mock(Promotion.class);
        Mockito.when(stubPromotion.getId()).thenReturn(1L);
        Mockito.when(stubPromotion.getPromotionInfo()).thenReturn(PromotionInfo.builder().title("title").description("description").build());

        reductionRule.applyRule(stubTransactionContext, stubPromotion);

        Mockito.verify(stubTransactionContext).createNextTransactionContext(List.of(
                PricedTransactionItem.builder()
                        .id("1").price(new BigDecimal(50)).count(1)
                        .appliedPromotionInfo(new AppliedPromotionInfo(stubPromotion.getId(), stubPromotion.getPromotionInfo(), new BigDecimal(50))).build(),
                PricedTransactionItem.builder().id("2").price(new BigDecimal(80)).count(2)
                        .appliedPromotionInfo(null).build()
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

        Promotion stubPromotion = Mockito.mock(Promotion.class);
        Mockito.when(stubPromotion.getId()).thenReturn(1L);
        Mockito.when(stubPromotion.getPromotionInfo()).thenReturn(PromotionInfo.builder().title("title").description("description").build());

        reductionRule.applyRule(stubTransactionContext, stubPromotion);

        Mockito.verify(stubTransactionContext).createNextTransactionContext(List.of(
                PricedTransactionItem.builder()
                        .id("1").price(new BigDecimal(0)).count(1)
                        .appliedPromotionInfo(new AppliedPromotionInfo(stubPromotion.getId(), stubPromotion.getPromotionInfo(), new BigDecimal(100))).build(),
                PricedTransactionItem.builder().id("2").price(new BigDecimal(80)).count(2)
                        .appliedPromotionInfo(null).build()
        ));
    }

}