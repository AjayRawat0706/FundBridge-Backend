package com.fundbridge.investor.repository;

import com.fundbridge.investor.model.InvestorIndustryPreference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface InvestorIndustryPreferenceRepository extends JpaRepository<InvestorIndustryPreference, UUID> {
    void deleteByInvestorId(UUID investorId);

    List<InvestorIndustryPreference> findByInvestorId(UUID investorId);
}
