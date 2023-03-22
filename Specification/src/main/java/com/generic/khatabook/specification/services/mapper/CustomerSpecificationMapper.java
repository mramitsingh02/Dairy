package com.generic.khatabook.specification.services.mapper;

import com.generic.khatabook.common.model.Container;
import com.generic.khatabook.specification.entity.CustomerSpecification;
import com.generic.khatabook.specification.model.CustomerSpecificationDTO;
import com.generic.khatabook.specification.model.CustomerSpecificationUpdatable;
import com.generic.khatabook.specification.model.UnitOfMeasurement;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CustomerSpecificationMapper implements Mapper<CustomerSpecification, CustomerSpecificationDTO, CustomerSpecificationUpdatable> {


    @Override
    public CustomerSpecification mapToEntity(final CustomerSpecificationDTO dto) {
        return new CustomerSpecification(dto.id(), dto.name(), dto.description(), dto.version(), dto.khatabookId(), dto.customerId(), dto.specificationFor(), dto.price(), dto.unitOfMeasurement().getUnitType());
    }

    @Override
    public Container<CustomerSpecificationDTO, CustomerSpecificationUpdatable> mapToContainer(final CustomerSpecification customerSpecification) {
        if (Objects.isNull(customerSpecification)) {
            return Container.empty();
        }
        final CustomerSpecificationDTO dto = mapToDTO(customerSpecification);
        return Container.of(dto, dto.updatable());
    }

    @Override
    public CustomerSpecificationDTO mapToDTO(final CustomerSpecification customerSpecification) {
        return new CustomerSpecificationDTO(customerSpecification.getId(), customerSpecification.getName(), customerSpecification.getDescription(), customerSpecification.getVersion(), customerSpecification.getKhatabookId(), customerSpecification.getCustomerId(), customerSpecification.getSpecificationFor(), customerSpecification.getPrice(), getUnitOfMeasurement(customerSpecification.getUnitOfMeasurement()));
    }


    private UnitOfMeasurement getUnitOfMeasurement(final String dbUnitOfMeasurement) {
        final UnitOfMeasurement dbValue;
        for (final UnitOfMeasurement value : UnitOfMeasurement.values()) {
            if (value.getUnitType().equals(dbUnitOfMeasurement)) {
                return value;
            }
        }
        return UnitOfMeasurement.NONE;
    }


}
