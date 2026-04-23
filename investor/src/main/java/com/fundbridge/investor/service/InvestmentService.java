package com.fundbridge.investor.service;

import com.fundbridge.investor.dto.InvestorPortfolioRequestDto;
import com.fundbridge.investor.dto.InvestorPortfolioResponseDto;
import com.fundbridge.investor.exception.ResourceAlreadyExistException;
import com.fundbridge.investor.exception.ResourceNotFoundException;
import com.fundbridge.investor.mapper.InvestorPortfolioMapper;
import com.fundbridge.investor.model.Investor;
import com.fundbridge.investor.model.InvestorPortfolio;
import com.fundbridge.investor.repository.InvestorPortfolioRepository;
import com.fundbridge.investor.repository.InvestorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class InvestmentService {
  private final InvestorPortfolioRepository investorPortfolioRepository;
  private final InvestorRepository investorRepository;
  public InvestorPortfolioResponseDto addInvestment(UUID userId, InvestorPortfolioRequestDto request){
      Investor investor = investorRepository.findByUserId(userId)
              .orElseThrow(() -> new ResourceNotFoundException("Investor not found"));

      //validate for startup when broken into microservices through interservice communication

      if(investorPortfolioRepository.existsByInvestorIdAndStartupId(investor.getId(), request.getStartupId())){
          throw new ResourceAlreadyExistException("Investment already exists for this startup");
      }
      InvestorPortfolio portfolio= InvestorPortfolioMapper.toPortfolio(investor.getId(),request);
      InvestorPortfolio savedPortfolio=investorPortfolioRepository.save(portfolio);
      return InvestorPortfolioMapper.toPortfolioResponse(savedPortfolio);
  }

  public List<InvestorPortfolioResponseDto>getPortfolio(UUID userId){
      Investor investor = investorRepository.findByUserId(userId)
              .orElseThrow(() -> new ResourceNotFoundException("Investor not found"));
      UUID investorId=investor.getId();
      return investorPortfolioRepository.findByInvestorId(investorId).stream()
              .map(InvestorPortfolioMapper::toPortfolioResponse)
              .toList();
  }

  //this api will be called by startup service by interservice communication
  public List<InvestorPortfolioResponseDto>getInvestors(UUID startupId){
      return investorPortfolioRepository.findByStartupId(startupId).stream()
              .map(InvestorPortfolioMapper::toPortfolioResponse)
              .toList();
  }
}
