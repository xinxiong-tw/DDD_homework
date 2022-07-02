package com.ddd.adapter.response;

import com.ddd.domain.promotion.entity.Promotion;
import com.ddd.domain.promotion.enums.PromotionStatus;
import com.ddd.domain.promotion.enums.PromotionType;
import lombok.Builder;

import java.time.OffsetDateTime;

@Builder
public class PageablePromotionResponse {
    private Long id;
    private PromotionType type;
    private OffsetDateTime startTime;
    private OffsetDateTime endTime;
    private PromotionStatus status;

    public static PageablePromotionResponse fromPromotion(Promotion promotion) {
        return PageablePromotionResponse.builder()
                .id(promotion.getId())
                .type(promotion.getType())
                .startTime(promotion.getStartTime())
                .endTime(promotion.getEndTime())
                .status(promotion.getStatus())
                .build();
    }
}
