package com.fundbridge.investor.repository;

import com.fundbridge.investor.model.FundingStage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FundingStageRepository extends JpaRepository<FundingStage,Integer> {
}
