package com.ddd.domain.calculation.valueObject;

import com.ddd.domain.calculation.entity.ChannelInfo;
import com.ddd.domain.calculation.entity.CustomerInfo;
import com.ddd.domain.calculation.entity.PricedTransactionItem;
import com.ddd.domain.calculation.entity.TransactionItem;

import java.util.List;

public class TransactionContext {
    public TransactionContext(CustomerInfo customerInfo, PriceTable priceTable, ChannelInfo channelInfo, List<TransactionItem> items) {
        this.customerInfo = customerInfo;
        this.priceTable = priceTable;
        this.channelInfo = channelInfo;
        this.items = items.stream()
                .map((it) -> PricedTransactionItem.builder()
                        .id(it.getId())
                        .count(it.getCount())
                        .price(priceTable.getPriceById(it.getId()))
                        .build()).toList();
    }

    private TransactionContext(CustomerInfo customerInfo, ChannelInfo channelInfo, List<PricedTransactionItem> items) {
        this.customerInfo = customerInfo;
        this.channelInfo = channelInfo;
        this.items = items;
    }

    private TransactionContext nextTransactionContext;
    private final CustomerInfo customerInfo;
    private PriceTable priceTable;
    private final ChannelInfo channelInfo;
    private final List<PricedTransactionItem> items;

    private void addTransactionContext(TransactionContext context) {
        TransactionContext lastContext = this;
        while (lastContext.nextTransactionContext != null) {
            lastContext = lastContext.nextTransactionContext;
        }
        lastContext.nextTransactionContext = context;
    }
    private TransactionContext setPricedTransactionContext(List<PricedTransactionItem> items) {
        return new TransactionContext(this.getCustomerInfo(), this.getChannelInfo(), items);
    }

    public List<PricedTransactionItem> getItems() {
        return this.items;
    }

    public CustomerInfo getCustomerInfo() {
        return customerInfo;
    }

    public PriceTable getPriceTable() {
        return priceTable;
    }

    public ChannelInfo getChannelInfo() {
        return channelInfo;
    }

    public TransactionContext getNextTransactionContext() {
        return nextTransactionContext;
    }

    public void addNextPricedTransactionItems(List<PricedTransactionItem> nextPricedTransactionItems) {
        addTransactionContext(setPricedTransactionContext(nextPricedTransactionItems));
    }

    public TransactionContext getLastTransactionContext() {
        TransactionContext lastContext = this;
        while (lastContext.getNextTransactionContext() != null) {
            lastContext = lastContext.getNextTransactionContext();
        }
        return lastContext;
    }
}
