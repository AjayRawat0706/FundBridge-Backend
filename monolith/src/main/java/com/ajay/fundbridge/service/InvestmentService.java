package com.ajay.fundbridge.service;

import com.ajay.fundbridge.dto.InvestorPortfolioRequestDto;
import com.ajay.fundbridge.dto.InvestorPortfolioResponseDto;
import com.ajay.fundbridge.exception.ResourceAlreadyExistException;
import com.ajay.fundbridge.exception.ResourceNotFoundException;
import com.ajay.fundbridge.mapper.InvestorPortfolioMapper;
import com.ajay.fundbridge.model.Investor;
import com.ajay.fundbridge.model.InvestorPortfolio;
import com.ajay.fundbridge.repository.InvestorPortfolioRepository;
import com.ajay.fundbridge.repository.InvestorRepository;
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
