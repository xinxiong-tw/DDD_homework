package com.ddd.domain.promotion.entity;

import com.ddd.domain.calculation.valueObject.PromotionInfo;
import com.ddd.domain.promotion.valueObject.constraints.PromotionConstraint;
import com.ddd.domain.promotion.valueObject.rule.PromotionRule;
import com.ddd.domain.promotion.enums.PromotionStatus;
import com.ddd.domain.promotion.enums.PromotionType;
import lombok.Getter;

import java.time.OffsetDateTime;

@Getter
public class Promotion {
    public Promotion(PromotionType type, String creatorId, OffsetDateTime startTime, OffsetDateTime endTime,
                     PromotionInfo promotionInfo, PromotionConstraint promotionConstraint,
                     PromotionRule rule, int priority) {
        OffsetDateTime now = OffsetDateTime.now();
        this.type = type;
        this.creatorId = creatorId;
        this.editorId = creatorId;
        this.createTime = now;
        this.lastEditTime = now;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = PromotionStatus.DRAFT;
        this.promotionInfo = promotionInfo;
        this.promotionConstraint = promotionConstraint;
        this.rule = rule;
        this.priority = priority;
        if (type == PromotionType.CODE_RELATED) {
            this.code = String.valueOf(Math.random());
        }
    }

    public Promotion(Long id, PromotionType type, String editorId, OffsetDateTime startTime,
                     OffsetDateTime endTime, PromotionInfo promotionInfo, PromotionConstraint promotionConstraint,
                     PromotionRule rule, int priority) {
        this.id = id;
        this.type = type;
        this.lastEditTime = OffsetDateTime.now();
        this.editorId = editorId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.promotionInfo = promotionInfo;
        this.promotionConstraint = promotionConstraint;
        this.rule = rule;
        this.priority = priority;
    }

    public Promotion(Long id, String code, PromotionType type, OffsetDateTime createTime, String creatorId,
                     OffsetDateTime lastEditTime, String editorId, OffsetDateTime startTime, OffsetDateTime endTime,
                     PromotionStatus status, PromotionInfo promotionInfo, PromotionConstraint promotionConstraint,
                     PromotionRule rule, int priority) {
        this.id = id;
        this.code = code;
        this.type = type;
        this.createTime = createTime;
        this.creatorId = creatorId;
        this.lastEditTime = lastEditTime;
        this.editorId = editorId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.promotionInfo = promotionInfo;
        this.promotionConstraint = promotionConstraint;
        this.rule = rule;
        this.priority = priority;
    }

    private Long id;

    private String code;
    private PromotionType type;
    private OffsetDateTime createTime;
    private String creatorId;
    private OffsetDateTime lastEditTime;
    private String editorId;
    private OffsetDateTime startTime;
    private OffsetDateTime endTime;
    private PromotionStatus status;
    private PromotionInfo promotionInfo;
    private PromotionConstraint promotionConstraint;
    private PromotionRule rule;
    private int priority;

    public void editInfo() {

    }
}
