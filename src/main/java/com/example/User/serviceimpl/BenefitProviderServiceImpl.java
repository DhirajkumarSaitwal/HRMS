package com.example.User.serviceimpl;

import com.example.User.entity.BenefitProvider;
import com.example.User.repository.BenefitProviderRepository;
import com.example.User.service.BenefitProviderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class BenefitProviderServiceImpl implements BenefitProviderService {

    private final BenefitProviderRepository providerRepo;

    public BenefitProviderServiceImpl(BenefitProviderRepository providerRepo) {
        this.providerRepo = providerRepo;
    }

    @Override
    public BenefitProvider createProvider(BenefitProvider provider) {

        return providerRepo.save(provider);
    }

    @Override
    public BenefitProvider updateProvider(Long id, BenefitProvider provider) {
        BenefitProvider existing = providerRepo.findById(Long.valueOf(String.valueOf(id)))
                .orElseThrow(() -> new IllegalArgumentException("Provider not found"));
        existing.setProviderName(provider.getProviderName());
        existing.setContactPerson(provider.getContactPerson());
        existing.setContactEmail(provider.getContactEmail());
        existing.setContactPhone(provider.getContactPhone());
        existing.setPolicyNumber(provider.getPolicyNumber());
        existing.setRenewalDate(provider.getRenewalDate());
        existing.setAddress(provider.getAddress());
        existing.setIsActive(provider.getIsActive());
        return providerRepo.save(existing);
    }

    @Override
    public void deleteProvider(Long id) {
        if (!providerRepo.existsById(id))
            throw new IllegalArgumentException("Provider not found");
        providerRepo.deleteById(id);
    }

    @Override
    public List<BenefitProvider> getAllProviders() {
        return providerRepo.findAll();
    }

    @Override
    public BenefitProvider getProviderById(Long id) {
        return providerRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Provider not found"));
    }
}

