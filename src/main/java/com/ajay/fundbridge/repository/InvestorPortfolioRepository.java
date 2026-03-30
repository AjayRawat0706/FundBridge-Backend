package com.ajay.fundbridge.repository;

import com.ajay.fundbridge.model.InvestorPortfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface InvestorPortfolioRepository  extends JpaRepository<InvestorPortfolio, UUID> {
}
