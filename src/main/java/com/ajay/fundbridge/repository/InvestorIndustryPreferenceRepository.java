package com.ajay.fundbridge.repository;

import com.ajay.fundbridge.model.InvestorIndustryPreference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InvestorIndustryPreferenceRepository extends JpaRepository<InvestorIndustryPreference, UUID> {
    void deleteByInvestorId(UUID investorId);
}
