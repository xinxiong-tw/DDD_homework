package com.ddd.adapter.request;

import com.ddd.domain.calculation.valueObject.CustomerRole;
import com.ddd.domain.promotion.enums.ConstraintType;
import com.ddd.domain.promotion.enums.Operator;
import com.ddd.domain.promotion.valueObject.constraints.*;
import com.ddd.domain.promotion.valueObject.productSet.ProductSet;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
@Setter
@Getter
public class ConstraintRequest {
    private ConstraintType type;
    private BigDecimal minAmount;
    private BigDecimal maxAmount;
    private List<String> productIds;

    private List<String> applicableChannelIds;

    private List<String> applicableRoles;

    private List<String> mustIncludedIds;
    private List<String> mustExcludedIds;

    private Operator operator;
    private List<ConstraintRequest> composedConstraints;

    public PromotionConstraint toPromotionConstraint() {
        if (this.type == ConstraintType.AMOUNT) {
            return AmountConstraint.builder()
                    .maxAmount(this.maxAmount)
                    .minAmount(this.minAmount)
                    .productSet(ProductSet.of(this.productIds))
                    .build();
        } else if (this.type == ConstraintType.CHANNEL) {
            return ChannelConstraint.builder()
                    .applicableChannelIds(this.applicableChannelIds)
                    .build();
        } else if (this.type == ConstraintType.CUSTOMER_ROLE) {
            return CustomerRoleConstraint.builder()
                    .applicableRoles(this.applicableRoles.stream().map(CustomerRole::new).toList())
                    .build();
        } else if (this.type == ConstraintType.ITEM) {
            return ItemConstraint.builder()
                    .mustExcludedProductSet(ProductSet.of(this.mustExcludedIds))
                    .mustIncludedProductSet(ProductSet.of(this.mustIncludedIds))
                    .build();
        } else if (this.type == ConstraintType.COMPOSED) {
            return ComposedConstraint.builder()
                    .operator(this.operator)
                    .constraints(this.composedConstraints.stream().map(ConstraintRequest::toPromotionConstraint).toList())
                    .build();
        }
        return null;
    }
}
