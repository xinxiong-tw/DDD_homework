package com.ddd.domain.promotion.valueObject.constraints;

import com.ddd.domain.calculation.valueObject.TransactionContext;

import java.util.List;

import com.ddd.domain.promotion.enums.ConstraintType;
import com.ddd.domain.promotion.enums.Operator;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ComposedConstraint extends PromotionConstraint {
    public ComposedConstraint(List<PromotionConstraint> constraints, Operator operator) {
        super(ConstraintType.COMPOSED);
        this.constraints = constraints;
        this.operator = operator;
    }

    private List<PromotionConstraint> constraints;
    private Operator operator;

    @Override
    public boolean isSatisfied(TransactionContext transactionContext) {
        return false;
    }
}
