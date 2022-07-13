package com.ddd.application;

import com.ddd.domain.calculation.valueObject.Transaction;
import com.ddd.domain.calculation.service.CalculationService;
import com.ddd.domain.calculation.valueObject.TransactionCalculatedResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CalculationApplication {
    private final CalculationService calculationService;
    public TransactionCalculatedResult calculate(Transaction transaction) {
        return calculationService.calculate(new CalculateTransactionCommand(transaction));
    }
}
