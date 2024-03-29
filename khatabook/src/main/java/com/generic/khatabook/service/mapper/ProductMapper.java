package com.generic.khatabook.service.mapper;

import com.generic.khatabook.common.model.ProductDTO;
import com.generic.khatabook.entity.Customer;
import com.generic.khatabook.entity.CustomerProduct;
public class ProductMapper {
    public static CustomerProduct mapToProduct(ProductDTO dto, Customer entity) {
        return CustomerProduct.builder().productId(dto.id()).productName(dto.name()).customer(entity).build();
    }
}
