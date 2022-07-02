package com.ddd.adapter.response;

import com.ddd.domain.promotion.entity.Promotion;
import lombok.Builder;

@Builder
public class PromotionDetailResponse {
    //此处省略响应的数据结构
    public static PromotionDetailResponse fromPromotion(Promotion promotion) {
        return PromotionDetailResponse.builder().build();
    }
}
