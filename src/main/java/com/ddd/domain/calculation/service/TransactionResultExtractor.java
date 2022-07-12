package com.ddd.domain.calculation.service;

import com.ddd.domain.calculation.entity.PricedTransactionItem;
import com.ddd.domain.calculation.valueObject.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class TransactionResultExtractor {
    public TransactionCalculatedResult compareAndExtractResult(List<TransactionContext> calculatedContexts) {
        return extractResult(getBestTransactionContext(calculatedContexts));
    }

    private TransactionContext getBestTransactionContext(List<TransactionContext> calculatedContexts) {
        return calculatedContexts.stream()
                .reduce(calculatedContexts.get(0),
                        (prev, it) -> getLastContextTotalPrice(it).compareTo(getLastContextTotalPrice(prev)) > 0 ? prev : it);
    }

    private BigDecimal getLastContextTotalPrice(TransactionContext transactionContext) {
        return getTotalPrice(transactionContext.getLastTransactionContext());
    }

    private BigDecimal getTotalPrice(TransactionContext it) {
        return it.getItems().stream()
                .map(PricedTransactionItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private TransactionCalculatedResult extractResult(TransactionContext context) {
        Map<String, CalculatedResultItem> itemsMap = context.getItems().stream()
                .map(CalculatedResultItem::createCalculatedResultItem)
                .map(it -> Map.entry(it.getId(), it))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        TransactionContext nextContext = context.getNextTransactionContext();
        while (nextContext != null) {
            nextContext.getItems().forEach(it -> itemsMap.get(it.getId()).addAppliedPromotion(it.getAppliedPromotionRuleInfo()));
            nextContext = nextContext.getNextTransactionContext();
        }
        List<CalculatedResultItem> items = itemsMap.values().stream().toList();
        return new TransactionCalculatedResult(items, new CalculatedResultSummary(getOriginTotalPrice(items), getTotalPromotions(items), getFinalTotalPrice(items)));
    }

    private BigDecimal getFinalTotalPrice(List<CalculatedResultItem> items) {
        return items.stream()
                .map(CalculatedResultItem::getFinalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal getOriginTotalPrice(List<CalculatedResultItem> items) {
        return items.stream()
                .map(CalculatedResultItem::getOriginPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private List<AppliedPromotionRuleInfo> getTotalPromotions(List<CalculatedResultItem> items) {
        return items.stream().flatMap(it -> it.getAppliedPromotionRuleInfos().stream()).collect(new PromotionCollector());
    }
}

class PromotionCollector implements Collector<AppliedPromotionRuleInfo, List<AppliedPromotionRuleInfo>, List<AppliedPromotionRuleInfo>> {

    @Override
    public Supplier<List<AppliedPromotionRuleInfo>> supplier() {
        return ArrayList::new;
    }

    @Override
    public BiConsumer<List<AppliedPromotionRuleInfo>, AppliedPromotionRuleInfo> accumulator() {
        return (promotions, item) -> {
            if (promotions.stream().anyMatch(it -> it.getId().equals(item.getId()))) {
                return;
            }
            promotions.add(item);
        };
    }

    @Override
    public BinaryOperator<List<AppliedPromotionRuleInfo>> combiner() {
        return null;
    }

    @Override
    public Function<List<AppliedPromotionRuleInfo>, List<AppliedPromotionRuleInfo>> finisher() {
        return null;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Set.of(Characteristics.IDENTITY_FINISH);
    }
}
