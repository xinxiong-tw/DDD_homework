package com.ddd.domain.promotion.valueObject.rule;

import com.ddd.domain.calculation.valueObject.PricedTransactionItem;
import com.ddd.domain.calculation.valueObject.AppliedPromotionInfo;
import com.ddd.domain.calculation.valueObject.TransactionContext;
import com.ddd.domain.promotion.entity.Promotion;
import com.ddd.domain.promotion.valueObject.productSet.ProductSet;
import com.ddd.domain.promotion.valueObject.Amount;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Builder
@Getter
public class ReductionRule implements PromotionRule {
    private Amount reduceAmount;
    private ProductSet reducibleProductSet;

    @Override
    public TransactionContext applyRule(TransactionContext transactionContext, Promotion promotion) {
        return transactionContext.createNextTransactionContext(getNextPricedTransactionItems(transactionContext, promotion));
    }

    private List<PricedTransactionItem> getNextPricedTransactionItems(TransactionContext transactionContext, Promotion promotion) {
        return transactionContext.getItems().stream()
                .map(it -> PricedTransactionItem.builder()
                        .id(it.getId())
                        .count(it.getCount())
                        .price(getDiscountedPrice(it))
                        .appliedPromotionInfo(getAppliedPromotionRuleInfo(it, promotion))
                        .build()
                ).toList();
    }

    private BigDecimal getDiscountedPrice(PricedTransactionItem pricedTransactionItem) {
        return Optional.of(pricedTransactionItem)
                .filter(it -> reducibleProductSet.include(it.getId()))
                .map(it -> it.getPrice().subtract(reduceAmount.getDiscountAmount()).max(BigDecimal.ZERO))
                .orElseGet(pricedTransactionItem::getPrice);
    }

    private AppliedPromotionInfo getAppliedPromotionRuleInfo(PricedTransactionItem pricedTransactionItem, Promotion promotion) {
        return Optional.of(pricedTransactionItem)
                .filter(it -> reducibleProductSet.include(it.getId()))
                .map(it -> new AppliedPromotionInfo(promotion.getId(), promotion.getPromotionInfo(), reduceAmount.getDiscountAmount().min(it.getPrice())))
                .orElse(null);
    }
}
