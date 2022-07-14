package com.ddd.application;

import com.ddd.domain.calculation.valueObject.Transaction;
import com.ddd.domain.calculation.valueObject.TransactionCalculatedResult;

public interface CalculationApplication {
    TransactionCalculatedResult calculate(Transaction transaction);
}
