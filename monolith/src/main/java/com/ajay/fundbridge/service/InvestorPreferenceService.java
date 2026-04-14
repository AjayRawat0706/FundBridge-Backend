package com.ajay.fundbridge.service;

import com.ajay.fundbridge.dto.InvestorPreferenceRequestDto;
import com.ajay.fundbridge.model.InvestorIndustryPreference;
import com.ajay.fundbridge.model.InvestorPreference;
import com.ajay.fundbridge.model.InvestorStagePreference;
import com.ajay.fundbridge.repository.InvestorIndustryPreferenceRepository;
import com.ajay.fundbridge.repository.InvestorPreferenceRepository;
import com.ajay.fundbridge.repository.InvestorStagePreferenceRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class InvestorPreferenceService {
    private final InvestorPreferenceRepository investorPreferenceRepository;
    private final InvestorIndustryPreferenceRepository investorIndustryPreferenceRepository;
    private final InvestorStagePreferenceRepository investorStagePreferenceRepository;

    @Transactional
    public void savePreferences(UUID investorId, InvestorPreferenceRequestDto request){

        investorIndustryPreferenceRepository.deleteByInvestorId(investorId);
        investorStagePreferenceRepository.deleteByInvestorId(investorId);
        investorPreferenceRepository.deleteByInvestorId(investorId);

        InvestorPreference preference = new InvestorPreference();
        preference.setInvestorId(investorId);
        preference.setInvestmentMin(request.getInvestmentMin());
        preference.setInvestmentMax(request.getInvestmentMax());

        investorPreferenceRepository.save(preference);

        List<InvestorIndustryPreference> industries =
                request.getIndustryIds().stream()
                        .map(industryId -> InvestorIndustryPreference.builder()
                                .investorId(investorId)
                                .industryId(industryId)
                                .build())
                        .toList();

        investorIndustryPreferenceRepository.saveAll(industries);

        List<InvestorStagePreference> stages =
                request.getStageIds().stream()
                        .map(stageId -> InvestorStagePreference.builder()
                                .investorId(investorId)
                                .stageId(stageId)
                                .build())
                        .toList();

        investorStagePreferenceRepository.saveAll(stages);
    }

    public InvestorPreferenceRequestDto getPreferences(UUID investorId){

        InvestorPreference preference = investorPreferenceRepository
                .findByInvestorId(investorId)
                .orElse(null);

        List<Integer> industries = investorIndustryPreferenceRepository
                .findByInvestorId(investorId)
                .stream()
                .map(InvestorIndustryPreference::getIndustryId)
                .toList();

        List<Integer> stages = investorStagePreferenceRepository
                .findByInvestorId(investorId)
                .stream()
                .map(InvestorStagePreference::getStageId)
                .toList();

        InvestorPreferenceRequestDto response = new InvestorPreferenceRequestDto();

        if(preference != null){
            response.setInvestmentMin(preference.getInvestmentMin());
            response.setInvestmentMax(preference.getInvestmentMax());
        }

        response.setIndustryIds(industries);
        response.setStageIds(stages);

        return response;
    }
}
