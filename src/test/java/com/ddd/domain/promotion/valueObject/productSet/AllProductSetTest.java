package com.ddd.domain.promotion.valueObject.productSet;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AllProductSetTest {

    @Test
    void should_be_include_for_any_id() {
        AllProductSet allProductSet = new AllProductSet();

        assertTrue(allProductSet.include("1"));
    }

    @Test
    void should_not_is_any_id_list() {
            AllProductSet allProductSet = new AllProductSet();
            assertFalse(allProductSet.isAllIn(List.of("1", "2", "3")));
    }

}