package com.ddd.domain.promotion.valueObject.rule;

import com.ddd.domain.calculation.valueObject.AppliedPromotionInfo;
import com.ddd.domain.calculation.valueObject.PricedTransactionItem;
import com.ddd.domain.calculation.valueObject.PromotionInfo;
import com.ddd.domain.calculation.valueObject.TransactionContext;
import com.ddd.domain.promotion.entity.Promotion;
import com.ddd.domain.promotion.valueObject.productSet.AllProductSet;
import com.ddd.domain.promotion.valueObject.productSet.ListProductSet;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;

class DiscountRuleTest {

    @Test
    void should_discount_all_products_by_20_percent() {
        DiscountRule discountRule = new DiscountRule(new BigDecimal("0.2"), new AllProductSet());
        TransactionContext stubTransactionContext = Mockito.mock(TransactionContext.class);

        Mockito.when(stubTransactionContext.getItems()).thenReturn(List.of(
                PricedTransactionItem.builder().id("1").price(new BigDecimal(100)).count(1).build(),
                PricedTransactionItem.builder().id("2").price(new BigDecimal(80)).count(2).build()
        ));

        Promotion stubPromotion = Mockito.mock(Promotion.class);
        Mockito.when(stubPromotion.getId()).thenReturn(1L);
        Mockito.when(stubPromotion.getPromotionInfo()).thenReturn(PromotionInfo.builder().title("title").description("description").build());

        discountRule.applyRule(stubTransactionContext, stubPromotion);

        Mockito.verify(stubTransactionContext).addNextPricedTransactionItems(List.of(
                PricedTransactionItem.builder().id("1").price(new BigDecimal(80)).count(1)
                        .appliedPromotionInfo(new AppliedPromotionInfo(stubPromotion.getId(), stubPromotion.getPromotionInfo(), new BigDecimal(20)))
                        .build(),
                PricedTransactionItem.builder().id("2").price(new BigDecimal(64)).count(2)
                        .appliedPromotionInfo(new AppliedPromotionInfo(stubPromotion.getId(), stubPromotion.getPromotionInfo(), new BigDecimal(16)))
                        .build()
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

        Promotion stubPromotion = Mockito.mock(Promotion.class);
        Mockito.when(stubPromotion.getId()).thenReturn(1L);
        Mockito.when(stubPromotion.getPromotionInfo()).thenReturn(PromotionInfo.builder().title("title").description("description").build());

        discountRule.applyRule(stubTransactionContext, stubPromotion);

        Mockito.verify(stubTransactionContext).addNextPricedTransactionItems(List.of(
                PricedTransactionItem.builder().id("1").price(new BigDecimal(80)).count(1)
                        .appliedPromotionInfo(new AppliedPromotionInfo(stubPromotion.getId(), stubPromotion.getPromotionInfo(), new BigDecimal(20)))
                        .build(),
                PricedTransactionItem.builder().id("2").price(new BigDecimal(80)).count(2)
                        .appliedPromotionInfo(null)
                        .build()
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

        Promotion stubPromotion = Mockito.mock(Promotion.class);
        Mockito.when(stubPromotion.getId()).thenReturn(1L);
        Mockito.when(stubPromotion.getPromotionInfo()).thenReturn(PromotionInfo.builder().title("title").description("description").build());

        discountRule.applyRule(stubTransactionContext, stubPromotion);

        Mockito.verify(stubTransactionContext).addNextPricedTransactionItems(List.of(
                PricedTransactionItem.builder().id("1").price(new BigDecimal(0)).count(1)
                        .appliedPromotionInfo(new AppliedPromotionInfo(stubPromotion.getId(), stubPromotion.getPromotionInfo(), new BigDecimal(100))).build(),
                PricedTransactionItem.builder().id("2").price(new BigDecimal(80)).count(2)
                        .appliedPromotionInfo(null).build()
        ));
    }

}