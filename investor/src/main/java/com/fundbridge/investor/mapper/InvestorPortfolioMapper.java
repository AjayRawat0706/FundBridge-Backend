package com.fundbridge.investor.mapper;

import com.fundbridge.investor.dto.InvestorPortfolioRequestDto;
import com.fundbridge.investor.dto.InvestorPortfolioResponseDto;
import com.fundbridge.investor.model.InvestorPortfolio;

import java.util.UUID;

public class InvestorPortfolioMapper {
    public static InvestorPortfolio toPortfolio(UUID investorId, InvestorPortfolioRequestDto request){
        InvestorPortfolio portfolio =new InvestorPortfolio();
        portfolio.setInvestorId(investorId);
        portfolio.setStartupId(request.getStartupId());
        portfolio.setInvestmentAmount(request.getInvestmentAmount());
        portfolio.setEquityPercentage(request.getEquityPercentage());
        portfolio.setInvestmentDate(request.getInvestmentDate());
        return portfolio;
    }

    public static InvestorPortfolioResponseDto toPortfolioResponse(InvestorPortfolio portfolio){
        InvestorPortfolioResponseDto response= new InvestorPortfolioResponseDto();
        response.setId(portfolio.getId());
        response.setStartupId(portfolio.getStartupId());
        response.setEquityPercentage(portfolio.getEquityPercentage());
        response.setInvestmentAmount(portfolio.getInvestmentAmount());
        response.setInvestmentDate(portfolio.getInvestmentDate());
        return response;
    }
}
