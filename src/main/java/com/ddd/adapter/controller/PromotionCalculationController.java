package com.ddd.adapter.controller;

import com.ddd.domain.calculation.valueObject.TransactionCalculatedResult;
import com.ddd.application.CalculationApplication;
import com.ddd.domain.calculation.valueObject.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PromotionCalculationController {
    private final CalculationApplication calculationApplication;

    @PostMapping("/calculate")
    @ResponseStatus(HttpStatus.OK)
    public TransactionCalculatedResult calculate(@RequestBody Transaction transaction) {
        return calculationApplication.calculate(transaction);
    }

}
