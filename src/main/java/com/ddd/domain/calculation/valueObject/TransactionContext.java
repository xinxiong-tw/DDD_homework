package com.ddd.domain.calculation.valueObject;

import java.util.List;

public class TransactionContext {
    public TransactionContext(CustomerInfo customerInfo, PriceTable priceTable, ChannelInfo channelInfo, List<TransactionItem> items) {
        this.customerInfo = customerInfo;
        this.channelInfo = channelInfo;
        this.items = items.stream()
                .map((it) -> PricedTransactionItem.builder()
                        .id(it.id())
                        .count(it.count())
                        .price(priceTable.getPriceById(it.id()))
                        .build()).toList();
    }

    private TransactionContext(CustomerInfo customerInfo, ChannelInfo channelInfo, List<PricedTransactionItem> items) {
        this.customerInfo = customerInfo;
        this.channelInfo = channelInfo;
        this.items = items;
    }

    private TransactionContext nextTransactionContext;
    private final CustomerInfo customerInfo;
    private final ChannelInfo channelInfo;
    private final List<PricedTransactionItem> items;

    public TransactionContext addTransactionContext(TransactionContext context) {
        getLastTransactionContext().nextTransactionContext = context;
        return this;
    }

    public TransactionContext createNextTransactionContext(List<PricedTransactionItem> items) {
        return new TransactionContext(this.getCustomerInfo(), this.getChannelInfo(), items);
    }

    public List<PricedTransactionItem> getItems() {
        return this.items;
    }

    public CustomerInfo getCustomerInfo() {
        return customerInfo;
    }

    public ChannelInfo getChannelInfo() {
        return channelInfo;
    }

    public TransactionContext getNextTransactionContext() {
        return nextTransactionContext;
    }

    public TransactionContext getLastTransactionContext() {
        TransactionContext lastContext = this;
        while (lastContext.getNextTransactionContext() != null) {
            lastContext = lastContext.getNextTransactionContext();
        }
        return lastContext;
    }
}
