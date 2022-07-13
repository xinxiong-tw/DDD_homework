package com.ddd.domain.calculation.valueObject;

import java.util.List;

public record Transaction(List<TransactionItem> items, List<String> promotionCodes, String channelId,
                          String customerId) {
}
