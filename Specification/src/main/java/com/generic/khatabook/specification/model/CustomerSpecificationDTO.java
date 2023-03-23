package com.generic.khatabook.specification.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CustomerSpecificationDTO(String id, String name, String description, int version, String customerId,
                                       String khatabookId, String specificationFor,
                                       List<CustomerProductSpecificationDTO> products, LocalDateTime createdOn,
                                       LocalDateTime updateOn, LocalDateTime deleteOn) {


    public CustomerSpecificationUpdatable updatable() {
        final List<CustomerProductSpecificationDTO> products = this.products;
        return new CustomerSpecificationUpdatable(this.id, this.name, this.description, this.version, this.khatabookId, this.customerId, this.specificationFor, products.stream().map(CustomerProductSpecificationDTO::updatable).collect(Collectors.toList()), createdOn, updateOn, deleteOn);
    }
    public CustomerSpecificationDTO copyOf( final String id, final String khatabookId, final String customerId) {
        return new CustomerSpecificationDTO(id, this.name, this.description, this.version, khatabookId, customerId, this.specificationFor, this.products, createdOn, updateOn, deleteOn);
    }

    public CustomerSpecificationDTO copyOf(final String khatabookId, final String customerId) {
        return new CustomerSpecificationDTO(this.id, this.name, this.description, this.version, khatabookId, customerId, this.specificationFor, this.products, createdOn, updateOn, deleteOn);
    }
}
