package com.ddd.application;

import com.ddd.domain.promotion.entity.Promotion;

import java.util.List;

public interface PromotionApplication {
    List<Promotion> selectPromotions(List<String> promotionCodes);
}
