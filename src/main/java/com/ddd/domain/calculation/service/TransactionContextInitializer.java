package com.ddd.domain.calculation.service;

import com.ddd.adapter.client.CustomerServiceClient;
import com.ddd.adapter.client.ProductServiceClient;
import com.ddd.domain.calculation.entity.ChannelInfo;
import com.ddd.domain.calculation.entity.CustomerInfo;
import com.ddd.domain.calculation.entity.TransactionItem;
import com.ddd.domain.calculation.valueObject.PriceTable;
import com.ddd.domain.calculation.valueObject.TransactionContext;
import com.ddd.domain.calculation.entity.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TransactionContextInitializer {
    private final ProductServiceClient productServiceClient;
    private final CustomerServiceClient customerServiceClient;
    public TransactionContext initializeContext(Transaction transaction) {
        List<TransactionItem> items = transaction.getItems();
        String channelId = transaction.getChannelId();
        String customerId = transaction.getCustomerId();
        PriceTable priceTable = productServiceClient.getPriceTableByIds(items.stream().map(TransactionItem::getId).toList());
        CustomerInfo customerInfo = customerServiceClient.getCustomerInfoById(customerId);
        ChannelInfo channelInfo = new ChannelInfo(channelId);
        return new TransactionContext(customerInfo, priceTable, channelInfo, items);
    }
}
