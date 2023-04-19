package com.generic.khatabook.exchanger;

import com.generic.khatabook.model.ProductDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface ProductClient {
    @GetExchange("/products")
    public ResponseEntity<ProductDTO> getAllProducts();

    @GetExchange("/product/{productId}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable String productId);


}
