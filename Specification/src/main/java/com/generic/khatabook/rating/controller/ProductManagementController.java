package com.generic.khatabook.rating.controller;

import com.generic.khatabook.common.exceptions.AppEntity;
import com.generic.khatabook.common.exceptions.IllegalArgumentException;
import com.generic.khatabook.common.exceptions.InvalidArgumentException;
import com.generic.khatabook.common.exceptions.NotFoundException;
import com.generic.khatabook.common.model.ProductDTO;
import com.generic.khatabook.common.model.ProductUpdatable;
import com.generic.khatabook.common.model.ProductViews;
import com.generic.khatabook.rating.services.IdGeneratorService;
import com.generic.khatabook.rating.services.ProductManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
@RestController
@RequestMapping("product-service")
@RequiredArgsConstructor
public class ProductManagementController {
    private final  ProductManagementService myProductManagementService;
    private  final IdGeneratorService myIdGeneratorService;


    @PostMapping(path = "/products")
    public ResponseEntity<?> createAll(@RequestBody List<ProductDTO> products) {

        if (Objects.nonNull(products) && !products.isEmpty()) {
            products.parallelStream().forEach(this::create);
            return ResponseEntity.accepted().build();
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping(path = "/product")
    public ResponseEntity<?> create(@RequestBody ProductDTO product) {
        final List<ProductDTO> entityModel = myProductManagementService.findProductByName(product.name());
        if (Objects.isNull(entityModel)) {
            return ResponseEntity.of(new NotFoundException(AppEntity.PRODUCT, product.name()).get()).build();
        }
        try {
            final ProductDTO newProduct = myProductManagementService.saveProduct(product.copyOf(myIdGeneratorService.generateId()));
            return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{productId}").buildAndExpand(product.id()).toUri()).body(newProduct);
        } catch (Exception e) {
            return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400), e.getMessage())).build();
        }
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable String productId) {
        final ProductDTO entityModel = myProductManagementService.findProductById(productId).get();
        if (Objects.isNull(entityModel)) {
            return ResponseEntity.of(new NotFoundException(AppEntity.PRODUCT, productId).get()).build();
        }

        return ResponseEntity.ok(entityModel);
    }

    @GetMapping("/product/")
    public ResponseEntity<ProductViews> searchProductByQuery(@RequestParam(required = false) String name,
                                                             @RequestParam(required = false) String unitOfMeasurement)
    {

        List<ProductDTO> products = null;
        try {
            if (Objects.nonNull(name)) {
                products = myProductManagementService.findProductByName(name);
            } else if (Objects.nonNull(unitOfMeasurement)) {
                products = myProductManagementService.findProductByUnitOfMeasurement(unitOfMeasurement);
            } else {
                return getAllProducts();
            }
            if (Objects.isNull(products)) {
                return ResponseEntity.of(new NotFoundException(AppEntity.PRODUCT, name).get()).build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.of(e.get()).build();
        }
        return ResponseEntity.ok(new ProductViews(products));
    }

    @GetMapping(path = "/products")
    public ResponseEntity<ProductViews> getAllProducts() {

        return ResponseEntity.ok(new ProductViews(myProductManagementService.findAllProducts()));
    }


    @DeleteMapping("/product/{productId}")
    public ResponseEntity<ProductDTO> deleteProductById(@PathVariable String productId) {
        final ProductDTO entityModel = myProductManagementService.findProductById(productId).get();
        if (Objects.isNull(entityModel)) {
            return ResponseEntity.of(new NotFoundException(AppEntity.PRODUCT, productId).get()).build();
        }

        myProductManagementService.delete(entityModel);
        return ResponseEntity.ok(entityModel);
    }


    @PutMapping("/product/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable String productId, @RequestBody ProductDTO product) {
        if (!myProductManagementService.findProductById(productId).isPresent()) {
            return ResponseEntity.of(new NotFoundException(AppEntity.PRODUCT, productId).get()).build();
        }

        final ProductDTO productDTO = myProductManagementService.updateProduct(product);
        return ResponseEntity.ok(productDTO);
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
