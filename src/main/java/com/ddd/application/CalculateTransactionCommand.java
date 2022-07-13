package com.ddd.application;

import com.ddd.domain.calculation.valueObject.Transaction;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CalculateTransactionCommand {
    private Transaction transaction;

    public Transaction getTransaction() {
        return transaction;
    }
}
