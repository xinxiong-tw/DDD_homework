package com.ddd.domain.calculation.service;

import com.ddd.adapter.client.CustomerServiceClient;
import com.ddd.adapter.client.ProductServiceClient;
import com.ddd.domain.calculation.entity.ChannelInfo;
import com.ddd.domain.calculation.entity.CustomerInfo;
import com.ddd.domain.calculation.entity.TransactionItem;
import com.ddd.domain.calculation.valueObject.PriceTable;
import com.ddd.domain.calculation.valueObject.TransactionContext;
import com.ddd.domain.calculation.entity.Transaction;

import java.util.List;

public class TransactionContextInitializer {
    public static TransactionContext initializeContext(Transaction transaction) {
        List<TransactionItem> items = transaction.getItems();
        String channelId = transaction.getChannelId();
        String customerId = transaction.getCustomerId();
        PriceTable priceTable = ProductServiceClient.getPriceTableByIds(items.stream().map((item) -> item.getId()).toList());
        CustomerInfo customerInfo = CustomerServiceClient.getCustomerInfoById(customerId);
        ChannelInfo channelInfo = new ChannelInfo(channelId);
        return new TransactionContext(customerInfo, priceTable, channelInfo, items);
    }
}
