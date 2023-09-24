package com.generic.khatabook.rating.services.mapper;

import com.generic.khatabook.common.model.Container;
import com.generic.khatabook.common.model.Mapper;
import com.generic.khatabook.rating.entity.Specification;
import org.springframework.stereotype.Component;
import com.generic.khatabook.common.model.*;
@Component
public class SpecificationMapper implements Mapper<Specification, SpecificationDTO, Void> {
    @Override
    public Container<SpecificationDTO, Void> mapToContainer(final Specification specification) {

        final SpecificationDTO specificationDTO = mapToDTO(specification);

        return Container.of(specificationDTO);
    }

    public SpecificationDTO mapToDTO(final Specification specification) {
        if (specification != null) {
            return new SpecificationDTO(specification.getId(),
                                        specification.getName(),
                                        specification.getDescription(),
                                        specification.getCreatedOn(),
                                        specification.getUpdatedOn());
        }
        return null;
    }


    public Specification mapToEntity(final SpecificationDTO specification) {
        return Specification.builder().id(specification.id()).name(specification.name()).description(specification.description()).createdOn(
                specification.createdOn()).updatedOn(specification.updatedOn()).build();
    }
}
