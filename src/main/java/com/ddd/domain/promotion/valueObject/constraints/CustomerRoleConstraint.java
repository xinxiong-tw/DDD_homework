package com.ddd.domain.promotion.valueObject.constraints;

import com.ddd.domain.calculation.valueObject.TransactionContext;
import com.ddd.domain.calculation.valueObject.CustomerRole;
import com.ddd.domain.promotion.enums.ConstraintType;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class CustomerRoleConstraint extends PromotionConstraint {
    public CustomerRoleConstraint(List<CustomerRole> applicableRoles) {
        super(ConstraintType.CUSTOMER_ROLE);
        this.applicableRoles = applicableRoles;
    }

    private List<CustomerRole> applicableRoles;

    @Override
    public boolean isSatisfied(TransactionContext transactionContext) {
        CustomerRole customerRole = transactionContext.getCustomerInfo().getCustomerRole();
        return applicableRoles.contains(customerRole);
    }
}
