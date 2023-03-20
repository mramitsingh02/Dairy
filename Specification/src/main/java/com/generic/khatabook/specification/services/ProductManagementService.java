package com.generic.khatabook.specification.services;

import com.generic.khatabook.common.model.Container;
import com.generic.khatabook.specification.model.ProductDTO;
import com.generic.khatabook.specification.model.ProductRatingDTO;
import com.generic.khatabook.specification.model.ProductUpdatable;

import java.util.List;

public interface ProductManagementService {

    List<ProductDTO> getAllProducts();

    List<ProductDTO> findProductByName(String productName);

    ProductDTO saveProduct(ProductDTO product);

    Container<ProductDTO, ProductUpdatable> findProductById(String productId);

    void delete(ProductDTO productDTO);

    ProductDTO updateProduct(ProductDTO entityModel);

    void saveProductRating(ProductRatingDTO productRatingDTO);
}
