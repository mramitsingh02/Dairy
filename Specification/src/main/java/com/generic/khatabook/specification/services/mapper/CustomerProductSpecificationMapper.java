package com.generic.khatabook.specification.services.mapper;

import com.generic.khatabook.common.model.Container;
import com.generic.khatabook.common.model.Mapper;
import com.generic.khatabook.specification.entity.CustomerProductSpecification;
import com.generic.khatabook.specification.entity.Product;
import com.generic.khatabook.specification.model.CustomerProductSpecificationDTO;
import com.generic.khatabook.specification.model.CustomerProductSpecificationUpdatable;
import com.generic.khatabook.specification.model.UnitOfMeasurement;
import com.generic.khatabook.specification.model.UnitOfValue;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CustomerProductSpecificationMapper implements Mapper<CustomerProductSpecification, CustomerProductSpecificationDTO, CustomerProductSpecificationUpdatable> {
    @Override
    public CustomerProductSpecification mapToEntity(final CustomerProductSpecificationDTO dto) {
        return CustomerProductSpecification.builder().cid(dto.id()).productId(dto.productId()).quantity(dto.quantity()).build();
    }

    @Override
    public CustomerProductSpecificationDTO mapToDTO(final CustomerProductSpecification entity) {
        return new CustomerProductSpecificationDTO(entity.getCid(), entity.getProductId(), entity.getQuantity(),
                                                   new UnitOfValue(entity.getPrice(), entity.getStartUnit(),
                                                                   entity.getEndUnit()),
                                                   getUnitOfMeasurement(entity.getUnitOfMeasurement())
                                                   );
    }

    private UnitOfMeasurement getUnitOfMeasurement(final String unitOfMeasurment) {
        final UnitOfMeasurement dbValue;
        for (final UnitOfMeasurement value : UnitOfMeasurement.values()) {
            if (value.getUnitType().equals(unitOfMeasurment)) {
                return value;
            }
        }
        return UnitOfMeasurement.NONE;
    }
    @Override
    public Container<CustomerProductSpecificationDTO, CustomerProductSpecificationUpdatable> mapToContainer(final CustomerProductSpecification entity) {

        if (Objects.isNull(entity)) {
            return Container.empty();
        }

        final CustomerProductSpecificationDTO dto = mapToDTO(entity);

        return Container.of(dto, dto.updatable());
    }
}
