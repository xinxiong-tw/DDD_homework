package com.ddd.adapter.repo;

import com.ddd.adapter.dao.PromotionConstraintDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionConstraintRepository extends JpaRepository<PromotionConstraintDao, Long> {
}
