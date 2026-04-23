package com.fundbridge.startup.repository;

import com.fundbridge.startup.model.FundingStage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FundingStageRepository extends JpaRepository<FundingStage,Integer> {
}
