package com.generic.khatabook.rating.services;


import com.generic.khatabook.rating.model.SpecificationDTO;

import java.util.List;

public interface SpecificationManagementService {
    SpecificationDTO addSpecification(SpecificationDTO specification);

    boolean findById(String id);
    SpecificationDTO find(String id);

    List<SpecificationDTO> findAll();

    void deleteSpecifications(SpecificationDTO specification);

    SpecificationDTO updateSpecification(SpecificationDTO specificationDTO);

}
