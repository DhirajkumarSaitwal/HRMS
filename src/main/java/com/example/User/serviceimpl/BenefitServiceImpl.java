package com.example.User.serviceimpl;


import com.example.User.entity.BenefitMaster;
import com.example.User.repository.BenefitRepository;
import com.example.User.repository.EmployeeBenefitMappingRepository;
import com.example.User.service.BenefitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class BenefitServiceImpl implements BenefitService {
@Autowired
    private  BenefitRepository benefitRepo;
    @Autowired
    private  EmployeeBenefitMappingRepository mappingRepo;

    public BenefitServiceImpl(BenefitRepository benefitRepo, EmployeeBenefitMappingRepository mappingRepo) {
        this.benefitRepo = benefitRepo;
        this.mappingRepo = mappingRepo;
    }

    @Override
    public BenefitMaster createBenefit(BenefitMaster benefit) {
        if (benefitRepo.findByBenefitName(benefit.getBenefitName()).isPresent()) {
            throw new IllegalArgumentException("Duplicate benefit name not allowed");
        }
        return benefitRepo.save(benefit);
    }


    @Override
    public BenefitMaster updateBenefit(Long id, BenefitMaster benefit) {
        BenefitMaster existing = benefitRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Benefit not found"));
        existing.setBenefitName(benefit.getBenefitName());
        existing.setBenefitType(benefit.getBenefitType());
        existing.setDescription(benefit.getDescription());
        existing.setIsActive(benefit.getIsActive());
        existing.setUpdatedBy(benefit.getUpdatedBy());
        existing.setCreatedBy(benefit.getCreatedBy());
        return benefitRepo.save(existing);
    }

    @Override
    public void deleteBenefit(Long id) {
        if (!benefitRepo.existsById(id))
            throw new IllegalArgumentException("Benefit not found");
        benefitRepo.deleteById(id);
    }

    @Override
    public List<BenefitMaster> getAllBenefits() {
        return benefitRepo.findAll();
    }


}
