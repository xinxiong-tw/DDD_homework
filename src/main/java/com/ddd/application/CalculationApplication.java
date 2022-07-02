package com.ddd.application;

import com.ddd.domain.calculation.service.CalculationService;
import com.ddd.domain.calculation.valueObject.TransactionCalculatedResult;
import com.ddd.domain.calculation.entity.Transaction;
import com.ddd.domain.promotion.entity.Promotion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CalculationApplication {
    private final CalculationService calculationService;
    private final PromotionApplication promotionApplication;
    public TransactionCalculatedResult calculate(Transaction transaction) {
        CalculateTransactionCommand calculateTransactionCommand = new CalculateTransactionCommand(transaction);
        List<String> promotionCodes = calculateTransactionCommand.getTransaction().getPromotionCodes();
        List<Promotion> promotions = promotionApplication.selectPromotions(promotionCodes);
        return calculationService.calculate(calculateTransactionCommand, promotions);
    }
}
