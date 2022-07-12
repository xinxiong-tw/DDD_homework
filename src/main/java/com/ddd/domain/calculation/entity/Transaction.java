package com.ddd.domain.calculation.entity;

import lombok.Getter;

import java.util.List;

@Getter
public class Transaction {
    private List<TransactionItem> items;
    private List<String> promotionCodes;
    private String channelId;
    private String customerId;
}
