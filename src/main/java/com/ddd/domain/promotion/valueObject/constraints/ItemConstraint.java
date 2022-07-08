package com.ddd.domain.promotion.valueObject.constraints;

import com.ddd.domain.calculation.entity.PricedTransactionItem;
import com.ddd.domain.calculation.valueObject.TransactionContext;
import com.ddd.domain.promotion.enums.ConstraintType;
import com.ddd.domain.promotion.valueObject.productSet.ProductSet;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Optional;

@Builder
@Getter
public class ItemConstraint extends PromotionConstraint {

    public ItemConstraint(ProductSet mustIncludedProductSet, ProductSet mustExcludedProductSet) {
        super(ConstraintType.ITEM);
        this.mustIncludedProductSet = mustIncludedProductSet;
        this.mustExcludedProductSet = mustExcludedProductSet;
    }

    private ProductSet mustIncludedProductSet;
    private ProductSet mustExcludedProductSet;

    @Override
    public boolean isSatisfied(TransactionContext transactionContext) {
        List<PricedTransactionItem> productItems = transactionContext.getItems();
        boolean isAllInMustIncludedList = Optional.ofNullable(mustIncludedProductSet)
                .map(mustIncludedProductSet -> mustIncludedProductSet.isAllIn(productItems.stream()
                        .map(PricedTransactionItem::getId)
                        .toList())
                ).orElse(true);
        boolean isNoneInBlockList = Optional.ofNullable(mustExcludedProductSet)
                .map(excludedProductSet -> productItems.stream()
                        .noneMatch(it -> excludedProductSet.include(it.getId())))
                .orElse(true);
        return isAllInMustIncludedList && isNoneInBlockList;
    }
}
