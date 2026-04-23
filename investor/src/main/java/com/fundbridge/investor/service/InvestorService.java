package com.fundbridge.investor.service;

import com.fundbridge.investor.dto.InvestorRequestDto;
import com.fundbridge.investor.dto.InvestorResponseDto;
import com.fundbridge.investor.exception.ResourceAlreadyExistException;
import com.fundbridge.investor.exception.ResourceNotFoundException;
import com.fundbridge.investor.exception.UnauthorizedException;
import com.fundbridge.investor.mapper.InvestorMapper;
import com.fundbridge.investor.model.Investor;
import com.fundbridge.investor.repository.InvestorPortfolioRepository;
import com.fundbridge.investor.repository.InvestorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class InvestorService {
    private final InvestorRepository investorRepository;
    private final InvestorPortfolioRepository investorPortfolioRepository;
    public InvestorResponseDto addInvestor(InvestorRequestDto request, UUID userId){
        //when it will be broken into microservices i will check for userid existence
        if(investorRepository.findByUserId(userId).isPresent()){
            throw new ResourceAlreadyExistException("Investor already present");
        }
        Investor investor= InvestorMapper.toInvestor(request,userId,0);
        Investor savedInvestor=investorRepository.save(investor);
        return InvestorMapper.toResponse(savedInvestor);
    }
    public InvestorResponseDto getInvestor(UUID id){
        Investor investor=investorRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Investor not found"));
        return InvestorMapper.toResponse(investor);
    }
    public List<InvestorResponseDto> getAllInvestors(){
        return investorRepository.findAll().stream()
                .map(InvestorMapper::toResponse)
                .toList();
    }

    public InvestorResponseDto updateInvestor(UUID id, UUID userId, InvestorRequestDto request){
        Investor investor=investorRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Investor not found"));
        if(!investor.getUserId().equals(userId)){
            throw new UnauthorizedException("You are not authorized");
        }
        investor.setInvestorType(request.getInvestorType());
        investor.setOrganizationName(request.getOrganizationName());
        investor.setBio(request.getBio());
        investor.setCompanyWebsite(request.getCompanyWebsite());
        investor.setLocation(request.getLocation());
        Investor updatedInvestor=investorRepository.save(investor);
        return InvestorMapper.toResponse(updatedInvestor);
    }
    public void deleteInvestor(UUID id, UUID userId){
        Investor investor=investorRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Investor not found"));
        if(!investor.getUserId().equals(userId)){
            throw new UnauthorizedException("You are not authorized");
        }
        investorRepository.delete(investor);
    }
}
