package com.ddd.adapter.dao;

import com.ddd.domain.calculation.valueObject.PromotionInfo;
import com.ddd.domain.promotion.entity.Promotion;
import com.ddd.domain.promotion.enums.PromotionStatus;
import com.ddd.domain.promotion.enums.PromotionType;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Table(name = "promotion")
public class PromotionDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private String type;

    private OffsetDateTime createTime;
    private String creatorId;
    private OffsetDateTime lastEditTime;
    private String editorId;
    private OffsetDateTime startTime;
    private OffsetDateTime endTime;

    @Enumerated(EnumType.STRING)
    private PromotionStatus status;

    private String title;
    private String description;

    @Type(type = "json")
    private List<String> tags;

    private int priority;

    @OneToOne
    @JoinColumn(name="promotion_constraint_id", referencedColumnName = "id")
    private PromotionConstraintDao promotionConstraint;

    @OneToOne
    @JoinColumn(name="promotion_rule_id", referencedColumnName = "id")
    private PromotionRuleDao rule;

    public Promotion toPromotion() {
        PromotionInfo promotionInfo = PromotionInfo.builder()
                .description(this.description)
                .title(this.title)
                .tags(this.tags)
                .build();
        return new Promotion(this.id, this.code, PromotionType.valueOf(this.type), this.createTime, this.creatorId, this.lastEditTime,
                this.editorId, this.startTime, this.endTime, this.status, promotionInfo, this.promotionConstraint.toPromotionConstraint(),
                this.rule.toPromotionRule(), this.priority);
    }

    private void setId(Long id) {
        this.id = id;
    }

    public static PromotionDao convertPromotionToDao(Promotion promotion) {
        return PromotionDao
                .builder()
                .code(promotion.getCode())
                .startTime(promotion.getStartTime())
                .endTime(promotion.getEndTime())
                .type(promotion.getType().getValue())
                .description(promotion.getPromotionInfo().getDescription())
                .creatorId(promotion.getCreatorId())
                .status(PromotionStatus.DRAFT)
                .title(promotion.getPromotionInfo().getTitle())
                .tags(promotion.getPromotionInfo().getTags())
                .priority(promotion.getPriority())
                .promotionConstraint(PromotionConstraintDao.convertConstraintToDao(promotion.getPromotionConstraint()))
                .build();
    }

    public static PromotionDao convertPromotionToDao(Long id, Promotion promotion) {
        PromotionDao promotionDao = PromotionDao.convertPromotionToDao(promotion);
        promotionDao.setId(id);
        return promotionDao;
    }
}
