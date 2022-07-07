package com.ddd.domain.promotion.valueObject.constraints;

import com.ddd.domain.calculation.entity.PricedTransactionItem;
import com.ddd.domain.calculation.valueObject.TransactionContext;
import com.ddd.domain.promotion.enums.ConstraintType;
import com.ddd.domain.promotion.valueObject.ProductSet;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

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
        boolean isAnyInAllowList = productItems.stream()
                .anyMatch((item) -> mustIncludedProductSet.include(item.getId()));
        boolean isAnyInBlockList = productItems.stream()
                .anyMatch((item) -> mustExcludedProductSet.include(item.getId()));
        return isAnyInAllowList && !isAnyInBlockList;
    }
}
