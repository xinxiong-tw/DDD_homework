package com.ddd.domain.promotion.valueObject.constraints;

import com.ddd.domain.calculation.valueObject.TransactionContext;
import com.ddd.domain.promotion.enums.ConstraintType;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ChannelConstraint extends PromotionConstraint {
    public ChannelConstraint(List<String> applicableChannelIds) {
        super(ConstraintType.CHANNEL);
        this.applicableChannelIds = applicableChannelIds;
    }

    private List<String> applicableChannelIds;

    @Override
    public boolean isSatisfied(TransactionContext transactionContext) {
        String channelId = transactionContext.getChannelInfo().getId();
        return applicableChannelIds.contains(channelId);
    }
}
