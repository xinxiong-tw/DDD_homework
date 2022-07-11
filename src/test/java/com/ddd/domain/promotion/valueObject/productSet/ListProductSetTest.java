package com.ddd.domain.promotion.valueObject.productSet;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ListProductSetTest {

    @Test
    void should_include_given_product_ids() {
        ListProductSet listProductSet = new ListProductSet(List.of("1", "2"));
        assertTrue(listProductSet.include("1"));
    }

    @Test
    void should_not_include_given_product_ids() {
        ListProductSet listProductSet = new ListProductSet(List.of("1", "2"));
        assertFalse(listProductSet.include("3"));
    }

    @Test
    void should_be_contained_in_given_products() {
        ListProductSet listProductSet = new ListProductSet(List.of("1", "2"));
        assertTrue(listProductSet.isAllIn(List.of("1", "2", "3", "4")));
    }

    @Test
    void should_not_be_contained_if_some_id_is_not_there() {
        ListProductSet listProductSet = new ListProductSet(List.of("1", "2"));
        assertFalse(listProductSet.isAllIn(List.of("1")));
    }
}