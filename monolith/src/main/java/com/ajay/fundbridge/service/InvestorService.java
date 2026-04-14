package com.ajay.fundbridge.service;

import com.ajay.fundbridge.dto.InvestorRequestDto;
import com.ajay.fundbridge.dto.InvestorResponseDto;
import com.ajay.fundbridge.exception.ResourceAlreadyExistException;
import com.ajay.fundbridge.exception.ResourceNotFoundException;
import com.ajay.fundbridge.exception.UnauthorizedException;
import com.ajay.fundbridge.mapper.InvestorMapper;
import com.ajay.fundbridge.model.Investor;
import com.ajay.fundbridge.repository.InvestorPortfolioRepository;
import com.ajay.fundbridge.repository.InvestorRepository;
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
