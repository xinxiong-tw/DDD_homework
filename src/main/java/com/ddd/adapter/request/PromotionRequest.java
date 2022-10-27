package com.ddd.adapter.request;

import com.ddd.domain.calculation.valueObject.PromotionInfo;
import com.ddd.domain.promotion.entity.Promotion;
import com.ddd.domain.promotion.enums.PromotionType;
import com.ddd.domain.promotion.valueObject.Amount;
import com.ddd.domain.promotion.valueObject.constraints.PromotionConstraint;
import com.ddd.domain.promotion.valueObject.productSet.ProductSet;
import com.ddd.domain.promotion.valueObject.rule.DiscountRule;
import com.ddd.domain.promotion.valueObject.rule.PromotionRule;
import com.ddd.domain.promotion.valueObject.rule.ReductionRule;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
public class PromotionRequest {
    private PromotionType type;

    private String creatorId;

    private String editorId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private LocalDateTime startTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private LocalDateTime endTime;

    private String title;
    private String description;

    private List<String> tags;

    private int priority;

    private ConstraintRequest constraint;

    private BigDecimal discountRate;
    private List<String> discountableProductIds;
    private BigDecimal reduceDiscountAmount;
    private List<String> reducibleProductIds;

    private PromotionRule convertDtoToPromotionRule() {
        if (this.discountRate != null) {
            return DiscountRule.builder()
                    .discountRate(this.discountRate)
                    .discountableProductSet(ProductSet.of(this.discountableProductIds))
                    .build();
        } else if (this.reduceDiscountAmount != null) {
            return ReductionRule.builder()
                    .reduceAmount(Amount.builder()
                            .discountAmount(this.reduceDiscountAmount)
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
                ZonedDateTime.of(this.startTime, ZoneId.systemDefault()).toOffsetDateTime(),
                ZonedDateTime.of(this.endTime, ZoneId.systemDefault()).toOffsetDateTime(),
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
                ZonedDateTime.of(this.startTime, ZoneId.systemDefault()).toOffsetDateTime(),
                ZonedDateTime.of(this.endTime, ZoneId.systemDefault()).toOffsetDateTime(),
                promotionInfo,
                promotionConstraint,
                promotionRule,
                priority
        );
    }
}
