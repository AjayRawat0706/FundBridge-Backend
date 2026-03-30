package com.ajay.fundbridge.repository;

import com.ajay.fundbridge.model.InvestorPreference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InvestorPreferenceRepository extends JpaRepository<InvestorPreference, UUID> {

    void deleteByInvestorId(UUID investorId);
}
