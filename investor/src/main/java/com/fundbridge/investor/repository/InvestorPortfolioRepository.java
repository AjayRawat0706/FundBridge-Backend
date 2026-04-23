package com.fundbridge.investor.repository;
import com.fundbridge.investor.model.InvestorPortfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface InvestorPortfolioRepository  extends JpaRepository<InvestorPortfolio, UUID> {
    boolean existsByInvestorIdAndStartupId(UUID investorId, UUID startupId);
    List<InvestorPortfolio> findByInvestorId(UUID investorId);

    List<InvestorPortfolio> findByStartupId(UUID startupId);
}
