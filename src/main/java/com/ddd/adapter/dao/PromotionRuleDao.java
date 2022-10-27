package com.ddd.adapter.dao;

import com.ddd.domain.promotion.valueObject.Amount;
import com.ddd.domain.promotion.valueObject.productSet.ProductSet;
import com.ddd.domain.promotion.valueObject.rule.DiscountRule;
import com.ddd.domain.promotion.valueObject.rule.PromotionRule;
import com.ddd.domain.promotion.valueObject.rule.ReductionRule;
import com.vladmihalcea.hibernate.type.json.JsonType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "promotion_rule")
@TypeDef(name = "json", typeClass = JsonType.class)
public class PromotionRuleDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal discountRate;
    @Type(type = "json")
    @Column(columnDefinition = "json")
    private List<String> discountableProductIds;

    private BigDecimal reduceAmount;
    @Type(type = "json")
    @Column(columnDefinition = "json")
    private List<String> reducibleProductIds;

    public PromotionRule toPromotionRule() {
        if (this.discountRate != null) {
            return DiscountRule.builder()
                    .discountRate(this.discountRate)
                    .discountableProductSet(ProductSet.of(this.discountableProductIds))
                    .build();
        } else if (this.reduceAmount != null) {
            return ReductionRule.builder()
                    .reducibleProductSet(ProductSet.of(this.reducibleProductIds))
                    .reduceAmount(Amount.builder()
                            .discountAmount(this.reduceAmount)
                            .build())
                    .build();
        }
        return null;
    }

    public static PromotionRuleDao convertRuleToDao(PromotionRule rule) {
        if (rule instanceof DiscountRule discountRule) {
            return PromotionRuleDao.builder()
                    .discountRate(discountRule.getDiscountRate())
                    .discountableProductIds(discountRule.getDiscountableProductSet().productIds())
                    .build();
        }
        if (rule instanceof ReductionRule reductionRule) {
            return PromotionRuleDao.builder()
                    .reduceAmount(reductionRule.getReduceAmount().getDiscountAmount())
                    .reducibleProductIds(reductionRule.getReducibleProductSet().productIds())
                    .build();
        }
        return null;
    }
}
