package com.generic.khatabook.rating.services.impl;

import com.generic.khatabook.common.exceptions.AppEntity;
import com.generic.khatabook.common.exceptions.IllegalArgumentException;
import com.generic.khatabook.common.exceptions.SubEntity;
import com.generic.khatabook.common.model.Container;
import com.generic.khatabook.common.model.ProductDTO;
import com.generic.khatabook.common.model.ProductUpdatable;
import com.generic.khatabook.common.model.RatingDTO;
import com.generic.khatabook.common.model.UnitOfMeasurement;
import com.generic.khatabook.rating.repository.ProductManagementRepository;
import com.generic.khatabook.rating.repository.ProductRatingRepository;
import com.generic.khatabook.rating.services.ProductManagementService;
import com.generic.khatabook.rating.services.mapper.ProductMapper;
import com.generic.khatabook.rating.services.mapper.ProductRatingMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Slf4j
@RequiredArgsConstructor
public class ProductManagementServiceImpl implements ProductManagementService {

    private final ProductManagementRepository myProductManagementRepository;
    private final ProductRatingRepository myProductRatingRepository;
    private final ProductMapper myProductMapper;
    private final ProductRatingMapper myProductRatingMapper;


    @Override
    public List<ProductDTO> findAllProducts() {
        return myProductMapper.mapToDTOs(myProductManagementRepository.findAll());
    }

    @Override
    public List<ProductDTO> findProductByName(final String productName) {
        return myProductMapper.mapToDTOs(myProductManagementRepository.findByName(productName));
    }

    @Override
    public ProductDTO saveProduct(final ProductDTO product) {

        return myProductMapper.mapToDTO(myProductManagementRepository.save(myProductMapper.mapToEntity(product)));
    }

    @Override
    public Container<ProductDTO, ProductUpdatable> findProductById(final String productId) {
        return myProductMapper.mapToContainer(myProductManagementRepository.findById(productId).orElse(null));
    }

    @Override
    public void delete(final ProductDTO productDTO) {
        myProductManagementRepository.delete(myProductMapper.mapToEntity(productDTO));
    }

    @Override
    public ProductDTO updateProduct(final ProductDTO product) {
        return myProductMapper.mapToDTO(myProductManagementRepository.save(myProductMapper.mapToEntity(product)));
    }

    @Override
    public void saveProductRating(final RatingDTO ratingDTO) {
        log.info(ratingDTO + " saving.");

        myProductRatingRepository.save(myProductRatingMapper.mapToEntity(ratingDTO));

        log.info(ratingDTO + " saved.");
    }

    @Override
    public List<RatingDTO> findProductRatingByProductId(final String productId) {
        return myProductRatingMapper.mapToDTOs(myProductRatingRepository.findByProductId(productId));
    }

    @Override
    public List<RatingDTO> findProductRatingByCustomerId(final String customerId) {
        return myProductRatingMapper.mapToDTOs(myProductRatingRepository.findByCustomerId(customerId));
    }

    @Override
    public List<ProductDTO> findProductByUnitOfMeasurement(final String unitOfMeasurement) {
        return myProductMapper.mapToDTOs(myProductManagementRepository.findByUnitOfMeasurement(checkAndGetUnitOfMeasurement(unitOfMeasurement).getUnitType()));
    }

    private static UnitOfMeasurement checkAndGetUnitOfMeasurement(final String unitOfMeasurement) {
        final UnitOfMeasurement unitOfMeasurementR;
        try {
            unitOfMeasurementR = UnitOfMeasurement.valueOf(unitOfMeasurement.toUpperCase());
        } catch (java.lang.IllegalArgumentException e) {
            throw new IllegalArgumentException(AppEntity.PRODUCT, SubEntity.UNIT_OF_MEASUREMENT, unitOfMeasurement + " No enum constant found");
        }
        return unitOfMeasurementR;
    }
}
