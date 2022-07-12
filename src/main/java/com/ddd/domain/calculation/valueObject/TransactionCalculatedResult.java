package com.ddd.domain.calculation.valueObject;

import java.util.List;

public record TransactionCalculatedResult(List<CalculatedResultItem> items, CalculatedResultSummary summary) {
}
