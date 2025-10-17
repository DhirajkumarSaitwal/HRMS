package com.example.User.service;

import com.example.User.entity.BenefitProvider;
import java.util.List;

public interface BenefitProviderService {
    BenefitProvider createProvider(BenefitProvider provider);
   // BenefitProvider updateProvider(String id, BenefitProvider provider);

    BenefitProvider updateProvider(Long id, BenefitProvider provider);

    void deleteProvider(Long id);
    List<BenefitProvider> getAllProviders();
  //  BenefitProvider getProviderById(String id);

    BenefitProvider getProviderById(Long id);
}
