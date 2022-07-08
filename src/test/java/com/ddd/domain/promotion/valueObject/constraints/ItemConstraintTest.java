package com.ddd.domain.promotion.valueObject.constraints;

import com.ddd.domain.calculation.entity.PricedTransactionItem;
import com.ddd.domain.calculation.valueObject.TransactionContext;
import com.ddd.domain.promotion.valueObject.productSet.ProductSet;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ItemConstraintTest {
    @Test
    void should_pass_if_includes_all_required_products() {
        ItemConstraint itemConstraint = new ItemConstraint(ProductSet.of(List.of("1", "2")), null);
        TransactionContext stubTransactionContext = Mockito.mock(TransactionContext.class);

        Mockito.when(stubTransactionContext.getItems()).thenReturn(List.of(
                PricedTransactionItem.builder().id("1").price(new BigDecimal(100)).count(1).build(),
                PricedTransactionItem.builder().id("2").price(new BigDecimal(100)).count(1).build()
        ));

        assertTrue(itemConstraint.isSatisfied(stubTransactionContext));
    }

    @Test
    void should_pass_if_not_includes_all_required_products() {
        ItemConstraint itemConstraint = new ItemConstraint(ProductSet.of(List.of("1", "2")), null);
        TransactionContext stubTransactionContext = Mockito.mock(TransactionContext.class);

        Mockito.when(stubTransactionContext.getItems()).thenReturn(List.of(
                PricedTransactionItem.builder().id("1").price(new BigDecimal(100)).count(1).build()
        ));

        assertFalse(itemConstraint.isSatisfied(stubTransactionContext));
    }

    @Test
    void should_pass_if_not_includes_any_excluded_products() {
        ItemConstraint itemConstraint = new ItemConstraint(null, ProductSet.of(List.of("2")));
        TransactionContext stubTransactionContext = Mockito.mock(TransactionContext.class);

        Mockito.when(stubTransactionContext.getItems()).thenReturn(List.of(
                PricedTransactionItem.builder().id("1").price(new BigDecimal(100)).count(1).build()
        ));

        assertTrue(itemConstraint.isSatisfied(stubTransactionContext));
    }

    @Test
    void should_not_pass_if_includes_any_excluded_products() {
        ItemConstraint itemConstraint = new ItemConstraint(null, ProductSet.of(List.of("2")));
        TransactionContext stubTransactionContext = Mockito.mock(TransactionContext.class);

        Mockito.when(stubTransactionContext.getItems()).thenReturn(List.of(
                PricedTransactionItem.builder().id("1").price(new BigDecimal(100)).count(1).build(),
                PricedTransactionItem.builder().id("2").price(new BigDecimal(100)).count(1).build(),
                PricedTransactionItem.builder().id("3").price(new BigDecimal(100)).count(1).build()
        ));

        assertFalse(itemConstraint.isSatisfied(stubTransactionContext));
    }

    @Test
    void should_pass_if_includes_all_required_products_and_not_includes_any_excluded_products() {
        ItemConstraint itemConstraint = new ItemConstraint(ProductSet.of(List.of("1", "2")), ProductSet.of(List.of("3")));
        TransactionContext stubTransactionContext = Mockito.mock(TransactionContext.class);

        Mockito.when(stubTransactionContext.getItems()).thenReturn(List.of(
                PricedTransactionItem.builder().id("1").price(new BigDecimal(100)).count(1).build(),
                PricedTransactionItem.builder().id("2").price(new BigDecimal(100)).count(1).build()
        ));

        assertTrue(itemConstraint.isSatisfied(stubTransactionContext));
    }
}