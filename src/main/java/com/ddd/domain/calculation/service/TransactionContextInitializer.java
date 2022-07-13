package com.ddd.domain.calculation.service;

import com.ddd.adapter.client.CustomerServiceClient;
import com.ddd.adapter.client.ProductServiceClient;
import com.ddd.domain.calculation.valueObject.ChannelInfo;
import com.ddd.domain.calculation.valueObject.CustomerInfo;
import com.ddd.domain.calculation.valueObject.TransactionItem;
import com.ddd.domain.calculation.valueObject.PriceTable;
import com.ddd.domain.calculation.valueObject.TransactionContext;
import com.ddd.domain.calculation.valueObject.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TransactionContextInitializer {
    private final ProductServiceClient productServiceClient;
    private final CustomerServiceClient customerServiceClient;
    public TransactionContext initializeContext(Transaction transaction) {
        List<TransactionItem> items = transaction.items();
        String channelId = transaction.channelId();
        String customerId = transaction.customerId();
        PriceTable priceTable = productServiceClient.getPriceTableByIds(items.stream().map(TransactionItem::id).toList());
        CustomerInfo customerInfo = customerServiceClient.getCustomerInfoById(customerId);
        ChannelInfo channelInfo = new ChannelInfo(channelId);
        return new TransactionContext(customerInfo, priceTable, channelInfo, items);
    }
}
