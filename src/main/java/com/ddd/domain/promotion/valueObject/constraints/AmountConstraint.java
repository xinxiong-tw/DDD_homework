package com.ddd.domain.promotion.valueObject.constraints;

import com.ddd.domain.calculation.valueObject.TransactionContext;
import com.ddd.domain.promotion.enums.ConstraintType;
import com.ddd.domain.promotion.valueObject.ProductSet;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
public class AmountConstraint extends PromotionConstraint{
    public AmountConstraint(BigDecimal minAmount, BigDecimal maxAmount, ProductSet productSet) {
        super(ConstraintType.AMOUNT);
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
        this.productSet = productSet;
    }

    private BigDecimal minAmount;
    private BigDecimal maxAmount;
    private ProductSet productSet;
    @Override
    public boolean isSatisfied(TransactionContext transactionContext) {
        return false;
    }
}
