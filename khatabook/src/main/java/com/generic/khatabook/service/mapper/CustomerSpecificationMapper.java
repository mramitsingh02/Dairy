package com.generic.khatabook.service.mapper;

import com.generic.khatabook.common.model.Container;
import com.generic.khatabook.common.model.Mapper;
import com.generic.khatabook.entity.CustomerProductSpecification;
import com.generic.khatabook.entity.CustomerSpecification;
import com.generic.khatabook.model.CustomerSpecificationDTO;
import com.generic.khatabook.model.CustomerSpecificationUpdatable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class CustomerSpecificationMapper implements Mapper<CustomerSpecification, CustomerSpecificationDTO, CustomerSpecificationUpdatable> {

    private final CustomerProductSpecificationMapper myCustomerProductSpecificationMapper;

    @Autowired
    public CustomerSpecificationMapper(final CustomerProductSpecificationMapper myCustomerProductSpecificationMapper) {
        this.myCustomerProductSpecificationMapper = myCustomerProductSpecificationMapper;
    }


    @Override
    public CustomerSpecification mapToEntity(final CustomerSpecificationDTO dto) {

        List<CustomerProductSpecification> allCustomerProductSpecification = Collections.emptyList();


        if (dto.products() != null && !(dto.products().isEmpty())) {
            allCustomerProductSpecification = dto.products().stream().map(myCustomerProductSpecificationMapper::mapToEntity).collect(
                    Collectors.toList());
        }
        return new CustomerSpecification(dto.id(),
                                         dto.name(),
                                         dto.description(),
                                         dto.version(),
                                         dto.khatabookId(),
                                         dto.customerId(),
                                         dto.specificationFor(),
                                         allCustomerProductSpecification,
                                         dto.createdOn(),
                                         dto.updateOn(),
                                         dto.deleteOn()

        );
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
        return new CustomerSpecificationDTO(customerSpecification.getCustomerSpecificationId(),
                customerSpecification.getSpecificationName(),
                customerSpecification.getDescription(),
                customerSpecification.getVersion(),
                customerSpecification.getCustomerId(),
                customerSpecification.getKhatabookId(),
                customerSpecification.getSpecificationFor(),
                myCustomerProductSpecificationMapper.mapToDTOs(customerSpecification.getCustomerProductSpecifications()),
                customerSpecification.getCreatedOn(),
                customerSpecification.getUpdatedOn(),
                customerSpecification.getDeletedOn());
    }

}
