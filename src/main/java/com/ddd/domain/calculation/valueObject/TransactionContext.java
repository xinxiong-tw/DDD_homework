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
        this.items = items.stream().map((item) -> {
            String id = item.getId();
            int count = item.getCount();
            Float priceById = priceTable.getPriceById(id);
            PricedTransactionItem pricedTransaction = PricedTransactionItem.builder()
                    .id(id)
                    .count(count)
                    .price(priceById)
                    .total(priceById * count)
                    .build();
            return pricedTransaction;
        }).toList();
    }

    private TransactionContext lastContext;
    private CustomerInfo customerInfo;
    private PriceTable priceTable;
    private ChannelInfo channelInfo;
    private List<PricedTransactionItem> items;

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
}
