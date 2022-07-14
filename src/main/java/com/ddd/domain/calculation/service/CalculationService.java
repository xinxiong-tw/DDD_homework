package com.ddd.domain.calculation.service;

import com.ddd.application.CalculateTransactionCommand;
import com.ddd.domain.calculation.valueObject.TransactionCalculatedResult;

public interface CalculationService {
    TransactionCalculatedResult calculate(CalculateTransactionCommand command);
}
