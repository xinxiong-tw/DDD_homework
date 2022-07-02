package com.ddd.application;

import com.ddd.domain.calculation.entity.Transaction;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CalculateTransactionCommand {
    private Transaction transaction;

    public Transaction getTransaction() {
        return transaction;
    }
}
