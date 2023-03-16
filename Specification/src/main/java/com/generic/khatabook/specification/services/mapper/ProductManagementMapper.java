package com.generic.khatabook.specification.services.mapper;

import com.generic.khatabook.specification.entity.Product;
import com.generic.khatabook.specification.model.ProductDTO;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Component
public class ProductManagementMapper {


    public Product mapToEntity(ProductDTO otherProduct) {

        return Product.builder().productId(otherProduct.productId()).name(otherProduct.name()).build();
    }

    public ProductDTO mapToDTO(Product thatProduct) {
        if (Objects.isNull(thatProduct)) {
            return null;
        }
        return new ProductDTO(thatProduct.getProductId(), thatProduct.getName(), 0f);
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
        return products.stream().map(this::mapToDTO).toList();
    }
}
