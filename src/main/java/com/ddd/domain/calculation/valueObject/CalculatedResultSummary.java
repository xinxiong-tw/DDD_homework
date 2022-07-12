package com.ddd.domain.calculation.valueObject;

import java.math.BigDecimal;
import java.util.List;

public record CalculatedResultSummary(BigDecimal originTotalPrice, List<AppliedPromotionRuleInfo> promotions,
                                      BigDecimal discountedTotalPrice) {
}
