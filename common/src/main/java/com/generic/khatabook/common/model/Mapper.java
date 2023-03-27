package com.generic.khatabook.common.model;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public interface Mapper<ENTITY, DTO, UPDATABLE> {


    ENTITY mapToEntity(DTO dto);

    Container<DTO, UPDATABLE> mapToContainer(ENTITY entity);

    DTO mapToDTO(ENTITY entity);

    default List<ENTITY> mapToEntities(List<DTO> dtos) {
        if (Objects.isNull(dtos)) {
            return Collections.emptyList();
        }
        return dtos.stream().map(this::mapToEntity).toList();
    }

    default List<DTO> mapToDTOs(final List<ENTITY> entities) {
        if (Objects.isNull(entities)) {
            return Collections.emptyList();
        }
        return entities.stream().map(this::mapToDTO).toList();
    }

    default Containers<DTO, UPDATABLE> mapToContainers(List<ENTITY> entities) {
        return new Containers<>(entities.stream().map(this::mapToContainer).toList());
    }

}