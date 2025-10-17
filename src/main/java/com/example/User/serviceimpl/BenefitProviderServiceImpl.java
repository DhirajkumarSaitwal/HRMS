package com.example.User.serviceimpl;

import com.example.User.dto.BenefitProviderDTO;
import com.example.User.entity.BenefitProvider;
import com.example.User.repository.BenefitProviderRepository;
import com.example.User.service.BenefitProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BenefitProviderServiceImpl implements BenefitProviderService {

    @Autowired
    private BenefitProviderRepository providerRepo;

    @Override
    public BenefitProviderDTO createProvider(BenefitProviderDTO dto) {
        BenefitProvider provider = new BenefitProvider();
        provider.setProviderName(dto.getProviderName());
        provider.setContactPerson(dto.getContactPerson());
        provider.setContactEmail(dto.getContactEmail());
        provider.setContactPhone(dto.getContactPhone());
        provider.setPolicyNumber(dto.getPolicyNumber());
        provider.setRenewalDate(dto.getRenewalDate());
        provider.setAddress(dto.getAddress());
        provider.setIsActive(dto.getIsActive());
        provider.setCreatedAt(LocalDateTime.now());
        provider.setUpdatedAt(LocalDateTime.now());
        providerRepo.save(provider);
        return dto;
    }

    @Override
    public BenefitProviderDTO getProviderById(Long providerId) {
        BenefitProvider entity = providerRepo.findById(providerId)
                .orElseThrow(() -> new RuntimeException("Provider not found with ID: " + providerId));
        BenefitProviderDTO dto = new BenefitProviderDTO();
        dto.setProviderName(entity.getProviderName());
        dto.setContactPerson(entity.getContactPerson());
        dto.setContactEmail(entity.getContactEmail());
        dto.setContactPhone(entity.getContactPhone());
        dto.setPolicyNumber(entity.getPolicyNumber());
        dto.setRenewalDate(entity.getRenewalDate());
        dto.setAddress(entity.getAddress());
        dto.setIsActive(entity.getIsActive());
        dto.setCreatedAt(LocalDateTime.now());
        dto.setUpdatedAt(LocalDateTime.now());
        return dto;
    }

    @Override
    public List<BenefitProviderDTO> getAllProviders() {
        return providerRepo.findAll().stream().map(p -> {
            BenefitProviderDTO dto = new BenefitProviderDTO();
            dto.setProviderName(p.getProviderName());
            dto.setContactPerson(p.getContactPerson());
            dto.setContactEmail(p.getContactEmail());
            dto.setContactPhone(p.getContactPhone());
            dto.setPolicyNumber(p.getPolicyNumber());
            dto.setRenewalDate(p.getRenewalDate());
            dto.setAddress(p.getAddress());
            dto.setIsActive(p.getIsActive());
            dto.setCreatedAt(LocalDateTime.now());
            dto.setUpdatedAt(LocalDateTime.now());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public BenefitProvider updateProvider(Long id, BenefitProvider entity) {
        BenefitProvider provider = providerRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Provider not found"));
        provider.setProviderName(entity.getProviderName());
        provider.setContactPerson(entity.getContactPerson());
        provider.setContactEmail(entity.getContactEmail());
        provider.setContactPhone(entity.getContactPhone());
        provider.setPolicyNumber(entity.getPolicyNumber());
        provider.setRenewalDate(entity.getRenewalDate());
        provider.setAddress(entity.getAddress());
        provider.setUpdatedAt(LocalDateTime.now());
        provider.setCreatedAt(LocalDateTime.now());
        provider.setIsActive(entity.getIsActive());
        return  providerRepo.save(provider);
    }

    @Override
    public void deleteProvider(Long id) {
        providerRepo.deleteById(id);
    }




}
