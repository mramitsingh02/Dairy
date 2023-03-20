package com.generic.khatabook.specification.services.mapper;

import com.generic.khatabook.common.model.Container;
import com.generic.khatabook.specification.entity.Product;
import com.generic.khatabook.specification.model.ProductDTO;
import com.generic.khatabook.specification.model.ProductUpdatable;
import com.generic.khatabook.specification.model.UnitOfMeasurement;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Component
public class ProductMapper {
    public Product mapToEntity(ProductDTO otherProduct) {
        return Product.builder().id(otherProduct.id()).name(otherProduct.name()).price(otherProduct.price()).unitOfMeasurement(otherProduct.unitOfMeasurement().getUnitType()).build();
    }

    public Container<ProductDTO, ProductUpdatable> mapToDTO(Product thatProduct) {
        if (Objects.isNull(thatProduct)) {
            return Container.empty();
        }


        final ProductDTO productDTO = new ProductDTO(thatProduct.getId(), thatProduct.getName(), thatProduct.getPrice(),
                                                     getUnitOfMeasurement(thatProduct), 0f);
        return Container.of(productDTO, productDTO.updatable());
    }

    private UnitOfMeasurement getUnitOfMeasurement(final Product thatProduct) {
        final UnitOfMeasurement dbValue;
        for (final UnitOfMeasurement value : UnitOfMeasurement.values()) {
            if (value.getUnitType().equals(thatProduct.getUnitOfMeasurement())) {
                return value;
            }
        }
        return UnitOfMeasurement.NONE;
    }

    public List<Product> mapToEntities(final List<ProductDTO> products) {
        if (Objects.isNull(products)) {
            return Collections.emptyList();
        }
        return products.stream().map(this::mapToEntity).toList();
    }

    public List<ProductDTO> mapToDTOs(final List<Product> products) {
        if (Objects.isNull(products)) {
            return Collections.emptyList();
        }
        return products.stream().map(this::mapToDTO).map(Container::get).toList();
    }
}
