package com.ddd.adapter.dao;

import com.ddd.domain.calculation.valueObject.CustomerRole;
import com.ddd.domain.promotion.enums.ConstraintType;
import com.ddd.domain.promotion.enums.Operator;
import com.ddd.domain.promotion.valueObject.IdAllowListProductSet;
import com.ddd.domain.promotion.valueObject.IdBlockListProductSet;
import com.ddd.domain.promotion.valueObject.constraints.*;
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
@Table(name = "promotion_constraint")
@TypeDef(name = "json", typeClass = JsonType.class)
public class PromotionConstraintDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ConstraintType type;

    private BigDecimal minAmount;
    private BigDecimal maxAmount;

    @Type(type = "json")
    @Column(columnDefinition = "json")
    private List<String> applicableChannelIds;

    @Type(type = "json")
    @Column(columnDefinition = "json")
    private List<String> applicableRoles;

    @Type(type = "json")
    @Column(columnDefinition = "json")
    private List<String> mustIncludedIds;

    @Type(type = "json")
    @Column(columnDefinition = "json")
    private List<String> mustExcludedIds;

    @Enumerated(EnumType.STRING)
    private Operator operator;

    @OneToMany
    private List<PromotionConstraintDao> composedConstraint;

    public PromotionConstraint toPromotionConstraint() {
        ConstraintType type = this.getType();
        if (type == ConstraintType.CUSTOMER_ROLE) {
            return new CustomerRoleConstraint(this.applicableRoles.stream().map(CustomerRole::new).toList());
        } else if (type == ConstraintType.AMOUNT) {
            return new AmountConstraint(this.minAmount, this.maxAmount,
                    IdAllowListProductSet.builder().productIds(this.mustIncludedIds).build()
            );
        } else if (type == ConstraintType.CHANNEL) {
            return new ChannelConstraint(this.applicableChannelIds);
        } else if (type == ConstraintType.ITEM) {
            return new ItemConstraint(IdAllowListProductSet.builder().productIds(this.mustIncludedIds).build(),
                    IdBlockListProductSet.builder().productIds(this.mustExcludedIds).build());
        } else if (type == ConstraintType.COMPOSED) {
            return new ComposedConstraint(this.composedConstraint.stream()
                    .map(PromotionConstraintDao::toPromotionConstraint)
                    .toList(),
                    this.operator);
        }
        return null;
    }

    public static PromotionConstraintDao convertConstraintToDao(PromotionConstraint constraint) {
        ConstraintType type = constraint.getType();
        if (type == ConstraintType.CUSTOMER_ROLE) {
            return PromotionConstraintDao.builder()
                    .type(ConstraintType.CUSTOMER_ROLE)
                    .applicableRoles(((CustomerRoleConstraint) constraint)
                            .getApplicableRoles().stream()
                            .map(CustomerRole::getName).toList())
                    .build();
        } else if (type == ConstraintType.AMOUNT) {
            AmountConstraint amountConstraint = (AmountConstraint) constraint;
            return PromotionConstraintDao.builder()
                    .type(ConstraintType.AMOUNT)
                    .maxAmount(amountConstraint.getMaxAmount())
                    .minAmount(amountConstraint.getMinAmount())
                    .mustIncludedIds(((IdAllowListProductSet) amountConstraint.getProductSet()).getProductIds())
                    .build();
        } else if (type == ConstraintType.CHANNEL) {
            ChannelConstraint channelConstraint = (ChannelConstraint) constraint;
            return PromotionConstraintDao.builder()
                    .type(ConstraintType.CHANNEL)
                    .applicableChannelIds(channelConstraint.getApplicableChannelIds())
                    .build();
        } else if (type == ConstraintType.ITEM) {
            ItemConstraint itemConstraint = (ItemConstraint) constraint;
            return PromotionConstraintDao.builder()
                    .type(ConstraintType.ITEM)
                    .mustIncludedIds(itemConstraint.getMustIncludedProductSet().getProductIds())
                    .mustExcludedIds(itemConstraint.getMustExcludedProductSet().getProductIds())
                    .build();
        } else if (type == ConstraintType.COMPOSED) {
            ComposedConstraint composedConstraint = (ComposedConstraint) constraint;
            return PromotionConstraintDao.builder()
                    .type(ConstraintType.COMPOSED)
                    .operator(composedConstraint.getOperator())
                    .composedConstraint(composedConstraint.getConstraints()
                            .stream()
                            .map(PromotionConstraintDao::convertConstraintToDao)
                            .toList())
                    .build();
        }
        return PromotionConstraintDao.builder().build();
    }
}
