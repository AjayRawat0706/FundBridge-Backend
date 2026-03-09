package com.ajay.fundbridge.repository;

import com.ajay.fundbridge.model.FundingStage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FundingStageRepository extends JpaRepository<FundingStage,Integer> {
}
