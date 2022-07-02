package com.ddd.adapter.repo;

import com.ddd.adapter.dao.PromotionRuleDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionRuleRepository extends JpaRepository<PromotionRuleDao, Long> {
}
