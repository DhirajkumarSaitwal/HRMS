package com.example.User.service;



import com.example.User.entity.BenefitMaster;
import com.example.User.entity.EmployeeBenefitMapping;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface BenefitService {
    BenefitMaster createBenefit(BenefitMaster benefit);
   // BenefitMaster updateBenefit(String id, BenefitMaster benefit);


    //void deleteBenefit(String id);

    BenefitMaster updateBenefit(Long id, BenefitMaster benefit);

    void deleteBenefit(Long id);

    List<BenefitMaster> getAllBenefits();

}