package com.ddd.adapter.repo;

import com.ddd.adapter.dao.PromotionDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PromotionRepository extends JpaRepository<PromotionDao, Long> {
    Optional<PromotionDao> findByCode(String code);
}
