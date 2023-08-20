package com.generic.khatabook.specification.services.mapper;

import com.generic.khatabook.common.model.Container;
import com.generic.khatabook.common.model.Mapper;
import com.generic.khatabook.specification.entity.ProductRating;
import com.generic.khatabook.specification.model.ProductRatingDTO;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Component
public class ProductRatingMapper implements Mapper<ProductRating, ProductRatingDTO, Void> {
    public ProductRating mapToEntity(ProductRatingDTO dto) {
        return ProductRating.builder().id(dto.id()).customerId(dto.customerId()).productId(dto.productId()).description(
                        dto.description()).rating(dto.rating())
                .build();
    }

    @Override
    public Container<ProductRatingDTO, Void> mapToContainer(final ProductRating product) {

        if (Objects.isNull(product)) {
            return Container.empty();
        }

        return Container.of(mapToDTO(product));
    }


    @Override
    public ProductRatingDTO mapToDTO(final ProductRating rating) {
        return new ProductRatingDTO(rating.getId(), rating.getCustomerId(), rating.getProductId(),
                rating.getRating(), rating.getDescription());
    }

    public List<ProductRating> mapToEntities(final List<ProductRatingDTO> products) {
        if (Objects.isNull(products)) {
            return Collections.emptyList();
        }
        return products.stream().map(this::mapToEntity).toList();
    }


}
