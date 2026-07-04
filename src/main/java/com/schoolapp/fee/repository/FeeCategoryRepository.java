package com.schoolapp.fee.repository;

import com.schoolapp.fee.entity.FeeCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeeCategoryRepository extends JpaRepository<FeeCategory, Long> {
    boolean existsByName(String name);
}