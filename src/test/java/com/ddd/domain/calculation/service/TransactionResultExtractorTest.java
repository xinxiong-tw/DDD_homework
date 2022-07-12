package com.ddd.domain.calculation.service;

import com.ddd.domain.calculation.entity.ChannelInfo;
import com.ddd.domain.calculation.entity.CustomerInfo;
import com.ddd.domain.calculation.entity.PricedTransactionItem;
import com.ddd.domain.calculation.entity.TransactionItem;
import com.ddd.domain.calculation.valueObject.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TransactionResultExtractorTest {

    void foo() {
        //        Promotion promotion1 = new Promotion(PromotionType.GENERIC, "creator", OffsetDateTime.now(), OffsetDateTime.now().plusDays(1),
//                PromotionInfo.builder().title("title").description("description").tags(List.of("tag1")).build(),
//                null,
//                DiscountRule.builder().discountableProductSet(new AllProductSet()).discountRate(new BigDecimal("0.3")).build(),
//                1);
//
//        Promotion promotion2 = new Promotion(PromotionType.GENERIC, "creator", OffsetDateTime.now(), OffsetDateTime.now().plusDays(1),
//                PromotionInfo.builder().title("title").description("description").tags(List.of("tag1")).build(),
//                null,
//                DiscountRule.builder().discountableProductSet(new AllProductSet()).discountRate(new BigDecimal("0.2")).build(),
//                1);
//
//        TransactionCalculator.calculate(context1, promotion1);
    }

    @Test
    void should_return_best_offer_promotion_and_extra_correct_result() {
        TransactionContext context1 = new TransactionContext(
                new CustomerInfo("user1", new CustomerRole("member")),
                new PriceTable(Map.of("1", new BigDecimal(100), "2", new BigDecimal(200))),
                new ChannelInfo("app"),
                List.of(new TransactionItem("1", 1), new TransactionItem("2", 2))
        );
        TransactionContext context2 = new TransactionContext(
                new CustomerInfo("user1", new CustomerRole("member")),
                new PriceTable(Map.of("1", new BigDecimal(100), "2", new BigDecimal(199))),
                new ChannelInfo("app"),
                List.of(new TransactionItem("1", 1), new TransactionItem("2", 2))
        );


        TransactionCalculatedResult transactionCalculatedResult = new TransactionResultExtractor().compareAndExtractResult(List.of(context1, context2));

        assertEquals(transactionCalculatedResult,
                new TransactionCalculatedResult(
                        List.of(
                                CalculatedResultItem.createCalculatedResultItem(PricedTransactionItem.builder()
                                        .id("1")
                                        .price(new BigDecimal(100))
                                        .count(1)
                                        .appliedPromotionInfo(null)
                                        .build()
                                ),
                                CalculatedResultItem.createCalculatedResultItem(PricedTransactionItem.builder()
                                        .id("2")
                                        .price(new BigDecimal(199))
                                        .count(2)
                                        .appliedPromotionInfo(null)
                                        .build()
                                )
                        ),
                        new CalculatedResultSummary(new BigDecimal(498), List.of(), new BigDecimal(498))
                )
        );
    }

    @Test
    void should_return_correct_result_after_one_promotion() {
        TransactionContext context1 = new TransactionContext(
                new CustomerInfo("user1", new CustomerRole("member")),
                new PriceTable(Map.of("1", new BigDecimal(100), "2", new BigDecimal(200))),
                new ChannelInfo("app"),
                List.of(new TransactionItem("1", 1), new TransactionItem("2", 2))
        );
        TransactionContext context2 = new TransactionContext(
                new CustomerInfo("user1", new CustomerRole("member")),
                new PriceTable(Map.of("1", new BigDecimal(100), "2", new BigDecimal(199))),
                new ChannelInfo("app"),
                List.of(new TransactionItem("1", 1), new TransactionItem("2", 2))
        );


        context1.addNextPricedTransactionItems(List.of(
                PricedTransactionItem.builder()
                        .id("1")
                        .price(new BigDecimal(90))
                        .count(1)
                        .appliedPromotionInfo(new AppliedPromotionInfo(
                                1L,
                                PromotionInfo.builder()
                                        .title("promotionTitle")
                                        .description("promotionDescription")
                                        .tags(List.of("test"))
                                        .build(),
                                new BigDecimal(10)))
                        .build(),
                PricedTransactionItem.builder()
                        .id("2")
                        .price(new BigDecimal(190))
                        .count(2)
                        .appliedPromotionInfo(new AppliedPromotionInfo(
                                1L,
                                PromotionInfo.builder()
                                        .title("promotionTitle")
                                        .description("promotionDescription")
                                        .tags(List.of("test"))
                                        .build(),
                                new BigDecimal(10)))
                        .build()
        ));


        TransactionCalculatedResult transactionCalculatedResult = new TransactionResultExtractor().compareAndExtractResult(List.of(context1, context2));

        assertEquals(new TransactionCalculatedResult(
                        List.of(
                                new CalculatedResultItem(
                                        "1",
                                        new BigDecimal(100), 1,
                                        List.of(
                                                new AppliedPromotionInfo(1L, PromotionInfo.builder()
                                                        .title("promotionTitle")
                                                        .description("promotionDescription")
                                                        .tags(List.of("test"))
                                                        .build(),
                                                        new BigDecimal(10))
                                        ),
                                        new BigDecimal(90)
                                ),
                                new CalculatedResultItem(
                                        "2",
                                        new BigDecimal(200),
                                        2,
                                        List.of(
                                                new AppliedPromotionInfo(1L,
                                                        PromotionInfo.builder()
                                                                .title("promotionTitle")
                                                                .description("promotionDescription")
                                                                .tags(List.of("test"))
                                                                .build(),
                                                        new BigDecimal(10))
                                        ),
                                        new BigDecimal(190)
                                )
                        ),
                        new CalculatedResultSummary(new BigDecimal(500), List.of(
                                new AppliedPromotionInfo(1L,
                                        PromotionInfo.builder()
                                                .title("promotionTitle")
                                                .description("promotionDescription")
                                                .tags(List.of("test"))
                                                .build(),
                                        new BigDecimal(30))
                        ), new BigDecimal(470))
                ),
                transactionCalculatedResult
        );

    }
}