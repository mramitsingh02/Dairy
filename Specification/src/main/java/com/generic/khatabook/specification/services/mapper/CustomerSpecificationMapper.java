package com.generic.khatabook.specification.services.mapper;

import com.generic.khatabook.specification.entity.CustomerSpecification;
import com.generic.khatabook.specification.model.CustomerSpecificationDTO;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class CustomerSpecificationMapper {


    public CustomerSpecification mapToEntity(CustomerSpecificationDTO thatCustomerSpecification) {

        return null;
    }

    public List<CustomerSpecification> mapToEntities(List<CustomerSpecificationDTO> otherCustomerSpecifications) {
        if (Objects.isNull(otherCustomerSpecifications)) {
            return Collections.emptyList();
        }
        return otherCustomerSpecifications.stream().map(this::mapToEntity).toList();
    }

    public CustomerSpecificationDTO mapToDTO(CustomerSpecification thatCustomerSpecification) {

        return null;
    }

    public List<CustomerSpecificationDTO> mapToDTOs(final List<CustomerSpecification> CustomerSpecifications) {
        if (Objects.isNull(CustomerSpecifications)) {
            return Collections.emptyList();
        }
        return CustomerSpecifications.stream().map(this::mapToDTO).toList();
    }


}
