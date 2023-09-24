package com.generic.khatabook.rating.services;

import com.generic.khatabook.common.model.Container;
import com.generic.khatabook.rating.model.ProductDTO;
import com.generic.khatabook.rating.model.ProductRatingDTO;
import com.generic.khatabook.rating.model.ProductUpdatable;

import java.util.List;

public interface ProductManagementService {

    List<ProductDTO> findAllProducts();

    List<ProductDTO> findProductByName(String productName);

    ProductDTO saveProduct(ProductDTO product);

    Container<ProductDTO, ProductUpdatable> findProductById(String productId);

    void delete(ProductDTO productDTO);

    ProductDTO updateProduct(ProductDTO entityModel);

    void saveProductRating(ProductRatingDTO productRatingDTO);

    List<ProductRatingDTO> findProductRatingByProductId(String productId);

    List<ProductRatingDTO> findProductRatingByCustomerId(String customerId);

    List<ProductDTO> findProductByUnitOfMeasurement(String unitOfMeasurement);
}
