package com.generic.khatabook.specification.services;

import com.generic.khatabook.specification.entity.Product;
import com.generic.khatabook.specification.model.ProductDTO;
import com.generic.khatabook.specification.model.ProductRatingDTO;

import java.util.List;

public interface ProductManagementService {

    List<ProductDTO> getAllProducts();

    List<ProductDTO> findProductByName(String productName);

    Product saveProduct(ProductDTO product);

    ProductDTO findProductById(String productId);

    void delete(ProductDTO productDTO);

    ProductDTO updateProduct(ProductDTO entityModel);

    void saveProductRating(ProductRatingDTO productRatingDTO);
}
