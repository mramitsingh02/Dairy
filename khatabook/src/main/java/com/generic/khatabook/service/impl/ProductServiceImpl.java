package com.generic.khatabook.service.impl;

import com.generic.khatabook.exceptions.AppEntity;
import com.generic.khatabook.exceptions.NotFoundException;
import com.generic.khatabook.exchanger.ProductClient;
import com.generic.khatabook.model.ProductDTO;
import com.generic.khatabook.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;
import java.util.Objects;
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductClient productClient;

    public ProductServiceImpl(ProductClient productClient) {
        this.productClient = productClient;
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return null;
    }

    public ProductDTO getCustomerProduct(final String productId) {
        try {
            final ResponseEntity<ProductDTO> responseEntity = productClient.getProductById(productId);
            if (Objects.isNull(responseEntity)) {
                throw new NotFoundException(AppEntity.PRODUCT, productId);
            } else {
                return responseEntity.getBody();
            }
        } catch (WebClientResponseException e) {
            throw new NotFoundException(AppEntity.PRODUCT, productId);
        }

    }
}
