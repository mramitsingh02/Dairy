package com.generic.khatabook.specification.services.mapper;

import com.generic.khatabook.common.model.Container;
import com.generic.khatabook.specification.entity.CustomerProductSpecification;
import com.generic.khatabook.specification.model.CustomerProductSpecificationDTO;
import com.generic.khatabook.specification.model.CustomerProductSpecificationUpdatable;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CustomerProductSpecificationMapper implements Mapper<CustomerProductSpecification, CustomerProductSpecificationDTO, CustomerProductSpecificationUpdatable> {
    @Override
    public CustomerProductSpecification mapToEntity(final CustomerProductSpecificationDTO dto) {
        return CustomerProductSpecification.builder().id(dto.id()).productId(dto.productId()).quantity(dto.quantity()).build();
    }

    @Override
    public CustomerProductSpecificationDTO mapToDTO(final CustomerProductSpecification entity) {
        return new CustomerProductSpecificationDTO(entity.getId(), entity.getProductId(), entity.getQuantity());
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
