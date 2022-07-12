package com.ddd.domain.calculation.service;

import com.ddd.application.CalculateTransactionCommand;
import com.ddd.application.PromotionApplication;
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
    private final PromotionApplication promotionApplication;
    private final TransactionContextInitializer transactionContextInitializer;
    private final TransactionResultExtractor transactionResultExtractor;
    public TransactionCalculatedResult calculate(CalculateTransactionCommand command) {
        Transaction transaction = command.getTransaction();
        List<Promotion> promotions = promotionApplication.selectPromotions(transaction.getPromotionCodes());
        TransactionContext transactionContext = transactionContextInitializer.initializeContext(transaction);
        List<TransactionContext> transactionContexts =
                promotions.stream()
                .map((promotion) -> TransactionCalculator.calculate(transactionContext, promotion)).toList();
        return transactionResultExtractor.compareAndExtractResult(transactionContexts);
    }
}
