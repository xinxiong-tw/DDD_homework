package com.ddd.domain.calculation.service;

import com.ddd.application.CalculateTransactionCommand;
import com.ddd.domain.calculation.valueObject.TransactionContext;
import com.ddd.domain.calculation.valueObject.TransactionCalculatedResult;
import com.ddd.domain.calculation.entity.Transaction;
import com.ddd.domain.promotion.entity.Promotion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CalculationService {
    public TransactionCalculatedResult calculate(CalculateTransactionCommand command, List<Promotion> promotions) {
        Transaction transaction = command.getTransaction();
        TransactionContext transactionContext = TransactionContextInitializer.initializeContext(transaction);
        List<TransactionContext> transactionContexts =
                promotions.stream()
                .map((promotion) -> TransactionCalculator.calculate(transactionContext, promotion)).toList();
        return TransactionResultExtractor.compareAndExtractResult(transactionContexts);
    }
}
