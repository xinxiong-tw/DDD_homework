package com.ddd.domain.promotion.valueObject.constraints;

import com.ddd.domain.calculation.entity.CustomerInfo;
import com.ddd.domain.calculation.valueObject.CustomerRole;
import com.ddd.domain.calculation.valueObject.TransactionContext;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CustomerRoleConstraintTest {
    @Test
    void should_pass_if_customer_role_is_included() {
        TransactionContext mock = Mockito.mock(TransactionContext.class);
        CustomerRoleConstraint roleConstraint = new CustomerRoleConstraint(List.of(
                new CustomerRole("member")
        ));

        Mockito.when(mock.getCustomerInfo()).thenReturn(new CustomerInfo("1", new CustomerRole("member")));

        assertTrue(roleConstraint.isSatisfied(mock));
    }

    @Test
    void should_pass_if_customer_role_is_not_included() {
        TransactionContext mock = Mockito.mock(TransactionContext.class);
        CustomerRoleConstraint roleConstraint = new CustomerRoleConstraint(List.of(
                new CustomerRole("member")
        ));

        Mockito.when(mock.getCustomerInfo()).thenReturn(new CustomerInfo("1", new CustomerRole("guest")));

        assertFalse(roleConstraint.isSatisfied(mock));
    }
}