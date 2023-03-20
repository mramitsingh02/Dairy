package com.generic.khatabook.specification.controller;

import com.generic.khatabook.exceptions.AppEntity;
import com.generic.khatabook.exceptions.InvalidArgumentException;
import com.generic.khatabook.exceptions.LimitBonusException;
import com.generic.khatabook.exceptions.NotFoundException;
import com.generic.khatabook.specification.model.ProductDTO;
import com.generic.khatabook.specification.model.ProductRatingDTO;
import com.generic.khatabook.specification.services.IdGeneratorService;
import com.generic.khatabook.specification.services.ProductManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController("product")
public class ProductManagementController {

    private ProductManagementService myProductManagementService;

    private IdGeneratorService myIdGeneratorService;

    @Autowired
    public ProductManagementController(final ProductManagementService productManagementService, final IdGeneratorService idGeneratorService) {
        this.myProductManagementService = productManagementService;
        this.myIdGeneratorService = idGeneratorService;
    }


    @GetMapping(path = "/products")
    public ResponseEntity<?> getAllProducts() {

        return ResponseEntity.ok(myProductManagementService.getAllProducts());
    }

    @PostMapping("/product/{productId}/rating")
    public ResponseEntity<?> saveProductRating(@RequestBody ProductRatingDTO productRatingDTO) {
        final ProductDTO product = myProductManagementService.findProductById(productRatingDTO.productId());
        if (Objects.isNull(product)) {
            return ResponseEntity.of(new NotFoundException(AppEntity.PRODUCT, productRatingDTO.productId()).get()).build();
        }
        if (productRatingDTO.rating() > 5) {
            return ResponseEntity.of(new LimitBonusException(AppEntity.PRODUCT, LimitBonusException.Limit.MAX, productRatingDTO.rating()).get()).build();
        } else if (productRatingDTO.rating() < 0) {
            return ResponseEntity.of(new LimitBonusException(AppEntity.PRODUCT, LimitBonusException.Limit.MIN, productRatingDTO.rating()).get()).build();
        }
        myProductManagementService.saveProductRating(productRatingDTO.copyOf(myIdGeneratorService.generateId()));
        return ResponseEntity.ok().build();
    }


    @PostMapping(path = "/product")
    public ResponseEntity<?> create(@RequestBody ProductDTO product) {
        final List<ProductDTO> entityModel = myProductManagementService.findProductByName(product.name());
        if (Objects.isNull(entityModel)) {
            return ResponseEntity.of(new NotFoundException(AppEntity.PRODUCT, product.name()).get()).build();
        }
        final ProductDTO newProduct = myProductManagementService.saveProduct(product.copyOf(myIdGeneratorService.generateId()));
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{productId}").buildAndExpand(product.id()).toUri()).body(newProduct);
    }

    @PostMapping(path = "/products")
    public ResponseEntity<?> createAll(@RequestBody List<ProductDTO> products) {

        if (Objects.nonNull(products)) {
            products.parallelStream().forEach(this::create);
            return ResponseEntity.accepted().build();
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable String id) {
        final ProductDTO entityModel = myProductManagementService.findProductById(id);
        if (Objects.isNull(entityModel)) {
            return ResponseEntity.of(new NotFoundException(AppEntity.PRODUCT, id).get()).build();
        }

        return ResponseEntity.ok(entityModel);
    }

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<?> deleteProductById(@PathVariable String id) {
        final ProductDTO entityModel = myProductManagementService.findProductById(id);
        if (Objects.isNull(entityModel)) {
            return ResponseEntity.of(new NotFoundException(AppEntity.PRODUCT, id).get()).build();
        }

        myProductManagementService.delete(entityModel);
        return ResponseEntity.ok(entityModel);
    }


    @PutMapping("/product/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable String productId, @RequestBody ProductDTO product) {
        final ProductDTO entityModel = myProductManagementService.findProductById(productId);
        if (Objects.isNull(entityModel)) {
            return ResponseEntity.of(new NotFoundException(AppEntity.PRODUCT, productId).get()).build();
        }
        myProductManagementService.updateProduct(entityModel);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(path = "/product/{productId}", consumes = "application/json-patch+json")
    public ResponseEntity<?> updatePartialProduct(@PathVariable String productId, @RequestBody Map<String, Object> productEntities) {
        final ProductDTO entityModel = myProductManagementService.findProductById(productId);
        if (Objects.isNull(entityModel)) {
            return ResponseEntity.of(new NotFoundException(AppEntity.PRODUCT, productId).get()).build();
        }
        for (final Map.Entry<String, Object> member : productEntities.entrySet()) {
            final Field field = ReflectionUtils.findField(ProductDTO.class, member.getKey());
            if (Objects.nonNull(field)) {
                ReflectionUtils.setField(field, entityModel, member.getValue());
            }
            throw new InvalidArgumentException(AppEntity.PRODUCT, member.getKey());
        }
        final ProductDTO updateProduct = myProductManagementService.updateProduct(entityModel);

        return ResponseEntity.ok(updateProduct);
    }
}
