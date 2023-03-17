package com.generic.khatabook.exchanger;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface ProductClient {
    @GetExchange("/products")
    public ResponseEntity<?> getAllProducts();

    @GetExchange("/product/id/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable String productId);


}
