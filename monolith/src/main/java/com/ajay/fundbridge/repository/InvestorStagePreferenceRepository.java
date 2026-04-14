package com.ajay.fundbridge.repository;

import com.ajay.fundbridge.model.InvestorStagePreference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface InvestorStagePreferenceRepository extends JpaRepository<InvestorStagePreference, UUID> {
    void deleteByInvestorId(UUID investorId);

    List<InvestorStagePreference> findByInvestorId(UUID investorId);
}
