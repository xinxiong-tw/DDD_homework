package com.ddd.domain.calculation.service;

import com.ddd.adapter.client.CustomerServiceClient;
import com.ddd.adapter.client.ProductServiceClient;
import com.ddd.application.CalculateTransactionCommand;
import com.ddd.application.PromotionApplication;
import com.ddd.domain.calculation.valueObject.*;
import com.ddd.domain.promotion.entity.Promotion;
import com.ddd.domain.promotion.enums.PromotionStatus;
import com.ddd.domain.promotion.enums.PromotionType;
import com.ddd.domain.promotion.valueObject.Amount;
import com.ddd.domain.promotion.valueObject.constraints.AmountConstraint;
import com.ddd.domain.promotion.valueObject.productSet.AllProductSet;
import com.ddd.domain.promotion.valueObject.productSet.ListProductSet;
import com.ddd.domain.promotion.valueObject.rule.DiscountRule;
import com.ddd.domain.promotion.valueObject.rule.PromotionRule;
import com.ddd.domain.promotion.valueObject.rule.ReductionRule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

class CalculationServiceTest {

    //
    @Test
    void should_return_calculation_result() {
        PromotionApplication stubPromotionApplication = Mockito.mock(PromotionApplication.class);
        ProductServiceClient stubProductServiceClient = Mockito.mock(ProductServiceClient.class);
        CustomerServiceClient stubCustomerServiceClient = Mockito.mock(CustomerServiceClient.class);

        CalculationService calculationService = new CalculationService(stubPromotionApplication, new TransactionContextInitializer(stubProductServiceClient, stubCustomerServiceClient), new TransactionResultExtractor());

        String channelId = "wechat";
        String customerId = "customer1";
        List<String> promotionCodes = List.of("1", "2");

        Promotion p1 = createTestPromotion(new AmountConstraint(new BigDecimal(100), null, new AllProductSet()), ReductionRule.builder()
                .reduceAmount(Amount.builder().discountAmount(new BigDecimal(30)).build())
                .reducibleProductSet(new AllProductSet())
                .build());
        Promotion p2 = createTestPromotion(new AmountConstraint(new BigDecimal(1000), null, new AllProductSet()),
                ReductionRule.builder()
                        .reduceAmount(Amount.builder().discountAmount(new BigDecimal(100)).build())
                        .reducibleProductSet(new AllProductSet())
                        .build());
        Promotion p3 = createTestPromotion(
                new AmountConstraint(new BigDecimal(200), null, new ListProductSet(List.of("1"))),
                ReductionRule.builder()
                        .reduceAmount(Amount.builder().discountAmount(new BigDecimal(50)).build())
                        .reducibleProductSet(new AllProductSet())
                        .build());
        Promotion p4 = createTestPromotion(
                new AmountConstraint(new BigDecimal(200), null, new AllProductSet()),
                DiscountRule.builder()
                        .discountRate(new BigDecimal("0.3"))
                        .discountableProductSet(new ListProductSet(List.of("2")))
                        .build()
        );
        Mockito.when(stubPromotionApplication.selectPromotions(promotionCodes)).thenReturn(List.of(p1, p2, p3, p4));

        Mockito.when(stubProductServiceClient.getPriceTableByIds(List.of("1", "2"))).thenReturn(new PriceTable(
                Map.of("1", new BigDecimal(100), "2", new BigDecimal(200))
        ));

        Mockito.when(stubCustomerServiceClient.getCustomerInfoById(customerId)).thenReturn(new CustomerInfo(
                customerId, new CustomerRole("member"))
        );

        TransactionCalculatedResult result = calculationService.calculate(new CalculateTransactionCommand(new Transaction(
                List.of(
                        new TransactionItem("1", 1),
                        new TransactionItem("2", 2)
                ),
                promotionCodes,
                channelId,
                customerId
        )));

        Assertions.assertEquals(result, new TransactionCalculatedResult(
                List.of(
                        new CalculatedResultItem("1", new BigDecimal(100), 1, List.of(), new BigDecimal(100)),
                        new CalculatedResultItem("2", new BigDecimal(200), 2, List.of(
                                new AppliedPromotionInfo(p4.getId(), p4.getPromotionInfo(), new BigDecimal(60))
                        ), new BigDecimal(140))
                ),
                new CalculatedResultSummary(new BigDecimal(500), List.of(
                        new AppliedPromotionInfo(p4.getId(), p4.getPromotionInfo(), new BigDecimal(120))
                ), new BigDecimal(380))
        ));
    }

    private Promotion createTestPromotion(AmountConstraint promotionConstraint, PromotionRule promotionRule) {
        String code = UUID.randomUUID().toString();
        return new Promotion(new Random().nextLong(), code, PromotionType.GENERIC, OffsetDateTime.now(), "xin", OffsetDateTime.now(),
                "xin", OffsetDateTime.now(), OffsetDateTime.now().plusDays(1L), PromotionStatus.PUBLISHED, PromotionInfo.builder()
                .title("title" + code).description("description" + code).tags(List.of("tag" + code)).build(),
                promotionConstraint,
                promotionRule,
                1);
    }
}