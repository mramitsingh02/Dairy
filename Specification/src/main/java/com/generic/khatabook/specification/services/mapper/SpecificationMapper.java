package com.generic.khatabook.specification.services.mapper;

import com.generic.khatabook.specification.entity.Specification;
import com.generic.khatabook.specification.model.SpecificationDTO;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class SpecificationMapper {
    public SpecificationDTO mapToDTO(final Optional<Specification> optionalSpecification) {
        return optionalSpecification.map(this::mapToDTO).orElse(null);
    }

    public SpecificationDTO mapToDTO(final Specification specification) {
        if (specification != null) {
            return new SpecificationDTO(specification.getId(), specification.getName(), specification.getDescription(), specification.getCreatedOn(), specification.getUpdatedOn());
        }
        return null;
    }


    public Specification mapToEntity(final SpecificationDTO specification) {
        return Specification.builder().id(specification.id()).name(specification.name()).description(specification.description()).createdOn(specification.createdOn()).updatedOn(specification.updatedOn()).build();
    }

    public List<SpecificationDTO> mapToDTOs(final List<Specification> allSpecifications) {
        if (Objects.isNull(allSpecifications)) {
            return Collections.emptyList();
        }
        return allSpecifications.stream().map(this::mapToDTO).toList();
    }
}
