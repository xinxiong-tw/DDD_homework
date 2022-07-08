package com.ddd.adapter.request;

import com.ddd.domain.calculation.valueObject.PromotionInfo;
import com.ddd.domain.promotion.entity.Promotion;
import com.ddd.domain.promotion.enums.PromotionType;
import com.ddd.domain.promotion.valueObject.Amount;
import com.ddd.domain.promotion.valueObject.productSet.ProductSet;
import com.ddd.domain.promotion.valueObject.constraints.PromotionConstraint;
import com.ddd.domain.promotion.valueObject.rule.DiscountRule;
import com.ddd.domain.promotion.valueObject.rule.PromotionRule;
import com.ddd.domain.promotion.valueObject.rule.ReductionRule;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

public class PromotionRequest {
    private PromotionType type;

    private String creatorId;

    private String editorId;
    private OffsetDateTime startTime;
    private OffsetDateTime endTime;

    private String title;
    private String description;

    private List<String> tags;

    private int priority;

    private ConstraintRequest constraint;

    private BigDecimal discountRate;
    private BigDecimal reduceMaxAmount;
    private BigDecimal reduceDiscountAmount;
    private List<String> reducibleProductIds;

    private PromotionRule convertDtoToPromotionRule() {
        if(this.discountRate != null) {
            return DiscountRule.builder().discountRate(this.discountRate).build();
        } else if (this.reducibleProductIds != null
                || this.reduceMaxAmount != null
                || this.reduceDiscountAmount != null) {
            return ReductionRule.builder()
                    .reduceAmount(Amount.builder()
                            .discountAmount(this.reduceDiscountAmount)
                            .maxAmount(this.reduceMaxAmount)
                            .build())
                    .reducibleProductSet(ProductSet.of(this.reducibleProductIds))
                    .build();
        }
        return null;
    }

    public Promotion toPromotion() {
        PromotionConstraint promotionConstraint = this.constraint.toPromotionConstraint();
        PromotionInfo promotionInfo = PromotionInfo.builder().title(this.title).description(this.description).tags(this.tags).build();
        PromotionRule promotionRule = this.convertDtoToPromotionRule();
        return new Promotion(
                this.type,
                this.creatorId,
                this.startTime,
                this.endTime,
                promotionInfo,
                promotionConstraint,
                promotionRule,
                this.priority
        );
    }

    public Promotion toPromotion(Long id) {
        PromotionConstraint promotionConstraint = this.constraint.toPromotionConstraint();
        PromotionInfo promotionInfo = PromotionInfo.builder().title(this.title).description(this.description).tags(this.tags).build();
        PromotionRule promotionRule = this.convertDtoToPromotionRule();
        return new Promotion(
                id,
                this.type,
                this.editorId,
                this.startTime,
                this.endTime,
                promotionInfo,
                promotionConstraint,
                promotionRule,
                priority
        );
    }
}
