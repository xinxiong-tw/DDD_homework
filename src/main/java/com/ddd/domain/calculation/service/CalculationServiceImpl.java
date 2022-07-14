package com.ddd.domain.calculation.service;

import com.ddd.application.CalculateTransactionCommand;
import com.ddd.domain.calculation.outbound.PromotionApplication;
import com.ddd.domain.calculation.valueObject.Transaction;
import com.ddd.domain.calculation.valueObject.TransactionCalculatedResult;
import com.ddd.domain.calculation.valueObject.TransactionContext;
import com.ddd.domain.promotion.entity.Promotion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CalculationServiceImpl implements CalculationService {
    private final PromotionApplication promotionApplication;
    private final TransactionContextInitializer transactionContextInitializer;
    private final TransactionResultExtractor transactionResultExtractor;
    private final TransactionCalculator transactionCalculator;
    @Override
    public TransactionCalculatedResult calculate(CalculateTransactionCommand command) {
        Transaction transaction = command.getTransaction();
        List<Promotion> promotions = promotionApplication.selectPromotions(transaction.promotionCodes());
        List<TransactionContext> transactionContexts =
                promotions.stream()
                .map((promotion) -> transactionCalculator.calculate(transactionContextInitializer.initializeContext(transaction), promotion)).toList();
        return transactionResultExtractor.compareAndExtractResult(transactionContexts);
    }
}
