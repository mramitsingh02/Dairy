package com.generic.khatabook.rating.services;

import com.generic.khatabook.common.model.Container;
import com.generic.khatabook.common.model.ProductDTO;
import com.generic.khatabook.common.model.ProductUpdatable;
import com.generic.khatabook.common.model.RatingDTO;

import java.util.List;
public interface ProductManagementService {

    List<ProductDTO> findAllProducts();

    List<ProductDTO> findProductByName(String productName);

    ProductDTO saveProduct(ProductDTO product);

    Container<ProductDTO, ProductUpdatable> findProductById(String productId);

    void delete(ProductDTO productDTO);

    ProductDTO updateProduct(ProductDTO entityModel);

    void saveProductRating(RatingDTO ratingDTO);

    List<RatingDTO> findProductRatingByProductId(String productId);

    List<RatingDTO> findProductRatingByCustomerId(String customerId);

    List<ProductDTO> findProductByUnitOfMeasurement(String unitOfMeasurement);
}
