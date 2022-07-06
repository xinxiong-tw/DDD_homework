package com.ddd.domain.promotion.valueObject.constraints;

import com.ddd.domain.calculation.entity.ChannelInfo;
import com.ddd.domain.calculation.valueObject.TransactionContext;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ChannelConstraintTest {

    @Test
    void should_pass_if_channel_is_included() {
        TransactionContext stubTransactionContext = Mockito.mock(TransactionContext.class);

        Mockito.when(stubTransactionContext.getChannelInfo()).thenReturn(new ChannelInfo("app"));

        ChannelConstraint constraint = new ChannelConstraint(List.of("app"));
        assertTrue(constraint.isSatisfied(stubTransactionContext));
    }

    @Test
    void should_fail_if_channel_is_not_included() {
        TransactionContext stubTransactionContext = Mockito.mock(TransactionContext.class);

        Mockito.when(stubTransactionContext.getChannelInfo()).thenReturn(new ChannelInfo("wechat"));

        ChannelConstraint constraint = new ChannelConstraint(List.of("app"));
        assertFalse(constraint.isSatisfied(stubTransactionContext));
    }

}