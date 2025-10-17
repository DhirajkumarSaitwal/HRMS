package com.example.User.service;


import com.example.User.entity.EmployeeBenefitMapping;

import java.util.List;

public interface EmployeeBenefitMappingService {

    EmployeeBenefitMapping createMapping(EmployeeBenefitMapping mapping);

   // EmployeeBenefitMapping updateMapping(String id, EmployeeBenefitMapping mapping);

  //  void deleteMapping(String id);

  //  EmployeeBenefitMapping getMappingById(String id);

    EmployeeBenefitMapping updateMapping(Long id, EmployeeBenefitMapping mapping);

    void deleteMapping(Long id);

    EmployeeBenefitMapping getMappingById(Long id);

    List<EmployeeBenefitMapping> getAllMappings();

    List<EmployeeBenefitMapping> getMappingsByEmployeeId(Long employeeId);
}

