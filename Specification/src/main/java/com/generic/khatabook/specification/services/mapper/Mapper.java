package com.generic.khatabook.specification.services.mapper;

import com.generic.khatabook.common.model.Container;
import com.generic.khatabook.common.model.Containers;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public interface Mapper<ENTITY, DTO, UPDATABLE> {


    ENTITY mapToEntity(DTO dto);

    default List<ENTITY> mapToEntities(List<DTO> dtos) {
        if (Objects.isNull(dtos)) {
            return Collections.emptyList();
        }
        return dtos.stream().map(this::mapToEntity).toList();
    }


    DTO mapToDTO(ENTITY entity);

    default List<DTO> mapToDTOs(final List<ENTITY> CustomerSpecifications) {
        if (Objects.isNull(CustomerSpecifications)) {
            return Collections.emptyList();
        }
        return CustomerSpecifications.stream().map(this::mapToDTO).toList();
    }

    Container<DTO, UPDATABLE> mapToContainer(ENTITY entity);

    default Containers<DTO, UPDATABLE> mapToContainers(List<ENTITY> entities) {
        return new Containers<>(entities.stream().map(this::mapToContainer).toList());
    }
}
