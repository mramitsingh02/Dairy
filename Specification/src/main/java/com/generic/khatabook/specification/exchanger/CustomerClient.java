package com.generic.khatabook.specification.exchanger;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;

public interface CustomerClient {
    @GetExchange("/khatabook/{khatabookId}/customer/{customerId}")
    public ResponseEntity<?> getCustomerByCustomerId(@PathVariable String khatabookId, @PathVariable String customerId);
}
