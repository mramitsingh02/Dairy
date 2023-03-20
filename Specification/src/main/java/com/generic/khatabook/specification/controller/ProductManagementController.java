package com.generic.khatabook.specification.controller;

import com.generic.khatabook.common.exceptions.AppEntity;
import com.generic.khatabook.common.exceptions.InvalidArgumentException;
import com.generic.khatabook.common.exceptions.LimitBonusException;
import com.generic.khatabook.common.exceptions.NotFoundException;
import com.generic.khatabook.specification.model.ProductDTO;
import com.generic.khatabook.specification.model.ProductRatingDTO;
import com.generic.khatabook.specification.model.ProductUpdatable;
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
import java.math.BigDecimal;
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
        final ProductDTO product = myProductManagementService.findProductById(productRatingDTO.productId()).get();
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
    public ResponseEntity<?> getProductById(@PathVariable String productId) {
        final ProductDTO entityModel = myProductManagementService.findProductById(productId).get();
        if (Objects.isNull(entityModel)) {
            return ResponseEntity.of(new NotFoundException(AppEntity.PRODUCT, productId).get()).build();
        }

        return ResponseEntity.ok(entityModel);
    }

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<?> deleteProductById(@PathVariable String productId) {
        final ProductDTO entityModel = myProductManagementService.findProductById(productId).get();
        if (Objects.isNull(entityModel)) {
            return ResponseEntity.of(new NotFoundException(AppEntity.PRODUCT, productId).get()).build();
        }

        myProductManagementService.delete(entityModel);
        return ResponseEntity.ok(entityModel);
    }


    @PutMapping("/product/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable String productId, @RequestBody ProductDTO product) {
        if (myProductManagementService.findProductById(productId).isPresent()) {
            return ResponseEntity.of(new NotFoundException(AppEntity.PRODUCT, productId).get()).build();
        }
        myProductManagementService.updateProduct(product);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(path = "/product/{productId}", consumes = "application/json-patch+json")
    public ResponseEntity<?> updatePartialProduct(@PathVariable String productId, @RequestBody Map<String, Object> productEntities) {
        final ProductUpdatable entityModel = myProductManagementService.findProductById(productId).updatable();
        if (Objects.isNull(entityModel)) {
            return ResponseEntity.of(new NotFoundException(AppEntity.PRODUCT, productId).get()).build();
        }
        for (final Map.Entry<String, Object> member : productEntities.entrySet()) {
            final Field field = ReflectionUtils.findField(ProductUpdatable.class, member.getKey());
            if (Objects.nonNull(field)) {
                ReflectionUtils.makeAccessible(field);
                final Object valueToSet = member.getValue();
                if (BigDecimal.class.getName().equals(field.getType().getName())) {
                    ReflectionUtils.setField(field, entityModel, BigDecimal.valueOf((Double) valueToSet));
                } else {
                    ReflectionUtils.setField(field, entityModel, valueToSet);
                }
            } else {
                throw new InvalidArgumentException(AppEntity.PRODUCT, member.getKey());
            }
        }
        final ProductDTO updateProduct = myProductManagementService.updateProduct(entityModel.build());

        return ResponseEntity.ok(updateProduct);
    }
}
