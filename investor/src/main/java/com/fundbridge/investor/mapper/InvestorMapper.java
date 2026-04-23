package com.fundbridge.investor.mapper;

import com.fundbridge.investor.dto.InvestorRequestDto;
import com.fundbridge.investor.dto.InvestorResponseDto;
import com.fundbridge.investor.model.Investor;

import java.util.UUID;

public class InvestorMapper {
    public static Investor toInvestor(InvestorRequestDto request, UUID userId,Integer portfolioSize){
        Investor investor=new Investor();
        investor.setUserId(userId);
        investor.setInvestorType(request.getInvestorType());
        investor.setBio(request.getBio());
        investor.setLocation(request.getLocation());
        investor.setCompanyWebsite(request.getCompanyWebsite());
        investor.setOrganizationName(request.getOrganizationName());
        investor.setPortfolioSize(portfolioSize);
        return investor;
    }

    public static InvestorResponseDto toResponse(Investor investor){
        InvestorResponseDto response =new InvestorResponseDto();
        response.setId(investor.getId());
        response.setInvestorType(investor.getInvestorType());
        response.setOrganizationName(investor.getOrganizationName());
        response.setCompanyWebsite(investor.getCompanyWebsite());
        response.setLocation(investor.getLocation());
        response.setBio(investor.getBio());
        response.setPortfolioSize(investor.getPortfolioSize());
        return response;
    }
}
