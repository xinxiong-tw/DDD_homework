package com.ddd.domain.promotion.valueObject.rule;

import com.ddd.domain.calculation.entity.PricedTransactionItem;
import com.ddd.domain.calculation.valueObject.TransactionContext;
import com.ddd.domain.promotion.valueObject.productSet.ProductSet;
import com.ddd.domain.promotion.valueObject.Amount;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Builder
public class ReductionRule implements PromotionRule {
    private Amount reduceAmount;
    private ProductSet reducibleProductSet;

    @Override
    public TransactionContext applyRule(TransactionContext transactionContext) {
        transactionContext.addNextPricedTransactionItems(getNextPricedTransactionItems(transactionContext));
        return transactionContext;
    }

    private List<PricedTransactionItem> getNextPricedTransactionItems(TransactionContext transactionContext) {
        return transactionContext.getItems().stream()
                .map(it -> PricedTransactionItem.builder()
                        .id(it.getId())
                        .count(it.getCount())
                        .price(calculateNextPrice(it))
                        .build()
                ).toList();
    }

    private BigDecimal calculateNextPrice(PricedTransactionItem pricedTransactionItem) {
        return Optional.of(pricedTransactionItem)
                .filter(it -> reducibleProductSet.include(it.getId()))
                .map(it -> it.getPrice().subtract(reduceAmount.getDiscountAmount()).max(BigDecimal.ZERO))
                .orElseGet(pricedTransactionItem::getPrice);
    }
}
