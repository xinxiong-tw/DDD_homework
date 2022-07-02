package com.ddd.domain.promotion.valueObject.constraints;

import com.ddd.domain.calculation.valueObject.TransactionContext;
import com.ddd.domain.promotion.enums.ConstraintType;

public abstract class PromotionConstraint {
    public PromotionConstraint(ConstraintType type) {
        this.type = type;
    }

    private ConstraintType type;

    public ConstraintType getType() {
        return type;
    }

    public abstract boolean isSatisfied(TransactionContext transactionContext);
}
