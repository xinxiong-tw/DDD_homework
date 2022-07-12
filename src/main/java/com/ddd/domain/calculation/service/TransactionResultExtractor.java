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
import java.util.stream.IntStream;

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
            nextContext.getItems().forEach(it -> itemsMap.get(it.getId()).addAppliedPromotion(it.getAppliedPromotionInfo()));
            nextContext = nextContext.getNextTransactionContext();
        }
        List<CalculatedResultItem> items = itemsMap.values().stream().toList();
        return new TransactionCalculatedResult(items, new CalculatedResultSummary(getOriginTotalPrice(items), getTotalPromotions(items), getFinalTotalPrice(items)));
    }

    private BigDecimal getFinalTotalPrice(List<CalculatedResultItem> items) {
        return items.stream()
                .map(CalculatedResultItem::getFinalTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal getOriginTotalPrice(List<CalculatedResultItem> items) {
        return items.stream()
                .map(CalculatedResultItem::getOriginTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private List<AppliedPromotionInfo> getTotalPromotions(List<CalculatedResultItem> items) {
        return items.stream()
                .flatMap(it -> it.getAppliedPromotionRuleInfos().stream()
                        .map(appliedPromotionInfo -> new AppliedPromotionInfo(
                                appliedPromotionInfo.id(),
                                appliedPromotionInfo.info(),
                                appliedPromotionInfo.promotionDiscount().multiply(new BigDecimal(it.getCount())))
                        )
                )
                .collect(new PromotionCollector());
    }
}

class PromotionCollector implements Collector<AppliedPromotionInfo, List<AppliedPromotionInfo>, List<AppliedPromotionInfo>> {

    @Override
    public Supplier<List<AppliedPromotionInfo>> supplier() {
        return ArrayList::new;
    }

    @Override
    public BiConsumer<List<AppliedPromotionInfo>, AppliedPromotionInfo> accumulator() {
        return (promotions, item) -> {
            int previousSamePromotionIndex = IntStream.range(0, promotions.size())
                    .filter(index -> promotions.get(index).id().equals(item.id()))
                    .findFirst()
                    .orElse(-1);
            if (previousSamePromotionIndex != -1) {
                AppliedPromotionInfo previousPromotionInfo = promotions.get(previousSamePromotionIndex);
                AppliedPromotionInfo newPromotionInfo = new AppliedPromotionInfo(previousPromotionInfo.id(),
                        previousPromotionInfo.info(),
                        previousPromotionInfo.promotionDiscount().add(item.promotionDiscount()));
                promotions.set(previousSamePromotionIndex, newPromotionInfo);
                return;
            }
            promotions.add(item);
        };
    }

    @Override
    public BinaryOperator<List<AppliedPromotionInfo>> combiner() {
        return null;
    }

    @Override
    public Function<List<AppliedPromotionInfo>, List<AppliedPromotionInfo>> finisher() {
        return null;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Set.of(Characteristics.IDENTITY_FINISH);
    }
}
