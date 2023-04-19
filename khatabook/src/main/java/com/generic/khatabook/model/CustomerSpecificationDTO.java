package com.generic.khatabook.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CustomerSpecificationDTO(String id, String name, String description, int version, String customerId,
                                       String khatabookId, String specificationFor,
                                       List<CustomerProductSpecificationDTO> products, LocalDateTime createdOn,
                                       LocalDateTime updateOn, LocalDateTime deleteOn) {
}
