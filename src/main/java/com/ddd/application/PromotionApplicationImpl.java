package com.ddd.application;

import com.ddd.adapter.dao.PromotionDao;
import com.ddd.adapter.repo.PromotionRepository;
import com.ddd.domain.calculation.outbound.PromotionApplication;
import com.ddd.domain.promotion.entity.Promotion;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PromotionApplicationImpl implements PromotionApplication {
    private final PromotionRepository promotionRepository;

    @Override
    public List<Promotion> selectPromotions(List<String> promotionCodes) {
        return promotionCodes.stream()
                .map(promotionRepository::findByCode)
                .filter(Optional::isPresent)
                .map((planOptional) -> planOptional.get().toPromotion())
                .toList();
    }

    public Long createPromotion(Promotion promotion) {
        PromotionDao promotionDao = promotionRepository.save(PromotionDao.convertPromotionToDao(promotion));
        return promotionDao.getId();
    }

    public Long editPromotion(Promotion promotion) {
        return promotionRepository.save(PromotionDao.convertPromotionToDao(promotion.getId(), promotion)).getId();
    }

    public Page<Promotion> getPromotions(int page, int size) {
        Pageable pageableInfo = PageRequest.of(page, size);
        return promotionRepository.findAll(pageableInfo).map(PromotionDao::toPromotion);
    }

    public Optional<Promotion> getPromotionById(Long id) {
        return promotionRepository.findById(id).map(PromotionDao::toPromotion);
    }
}
