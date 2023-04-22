package com.generic.khatabook.service;

import com.generic.khatabook.model.ProductDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;

import java.util.List;

public interface ProductService {
    public List<ProductDTO> getAllProducts();

    public ProductDTO getCustomerProduct(String productId);


}
