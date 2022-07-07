package com.ddd.adapter.dao;

import com.ddd.domain.promotion.valueObject.Amount;
import com.ddd.domain.promotion.valueObject.ProductSet;
import com.ddd.domain.promotion.valueObject.rule.DiscountRule;
import com.ddd.domain.promotion.valueObject.rule.PromotionRule;
import com.ddd.domain.promotion.valueObject.rule.ReductionRule;
import com.vladmihalcea.hibernate.type.json.JsonType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Data
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

    private BigDecimal reduceMaxAmount;
    private BigDecimal reduceDiscountAmount;

    @Type(type = "json")
    @Column(columnDefinition = "json")
    private List<String> reducibleProductIds;

    public PromotionRule toPromotionRule() {
        if (this.discountRate != null) {
            return DiscountRule.builder().discountRate(this.discountRate).build();
        } else if (this.reduceMaxAmount != null || this.reduceDiscountAmount != null || this.reducibleProductIds != null) {
            return ReductionRule.builder()
                    .reducibleProductSet(ProductSet.of(this.reducibleProductIds))
                    .reduceAmount(Amount.builder()
                            .maxAmount(this.reduceMaxAmount)
                            .discountAmount(this.reduceDiscountAmount)
                            .build())
                    .build();
        }
        return null;
    }
}
