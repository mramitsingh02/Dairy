package com.generic.khatabook.rating.services.impl;

import com.generic.khatabook.common.exceptions.AppEntity;
import com.generic.khatabook.common.exceptions.IllegalArgumentException;
import com.generic.khatabook.common.exceptions.SubEntity;
import com.generic.khatabook.common.model.Container;
import com.generic.khatabook.rating.model.ProductDTO;
import com.generic.khatabook.rating.model.ProductRatingDTO;
import com.generic.khatabook.rating.model.ProductUpdatable;
import com.generic.khatabook.rating.model.UnitOfMeasurement;
import com.generic.khatabook.rating.repository.ProductManagementRepository;
import com.generic.khatabook.rating.repository.ProductRatingRepository;
import com.generic.khatabook.rating.services.ProductManagementService;
import com.generic.khatabook.rating.services.mapper.ProductMapper;
import com.generic.khatabook.rating.services.mapper.ProductRatingMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProductManagementServiceImpl implements ProductManagementService {

    private ProductManagementRepository myProductManagementRepository;
    private ProductRatingRepository myProductRatingRepository;
    private ProductMapper myProductMapper;
    private ProductRatingMapper myProductRatingMapper;

    @Autowired
    public ProductManagementServiceImpl(final ProductManagementRepository thatProductManagementRepository,
                                        final ProductMapper thatProductMapper,
                                        final ProductRatingRepository thatProductRatingRepository,
                                        final ProductRatingMapper thatProductRatingMapper)
    {
        this.myProductManagementRepository = thatProductManagementRepository;
        this.myProductRatingRepository = thatProductRatingRepository;
        this.myProductMapper = thatProductMapper;
        this.myProductRatingMapper = thatProductRatingMapper;
    }


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
    public void saveProductRating(final ProductRatingDTO productRatingDTO) {
        log.info(productRatingDTO + " saving.");

        myProductRatingRepository.save(myProductRatingMapper.mapToEntity(productRatingDTO));

        log.info(productRatingDTO + " saved.");
    }

    @Override
    public List<ProductRatingDTO> findProductRatingByProductId(final String productId) {
        return myProductRatingMapper.mapToDTOs(myProductRatingRepository.findByProductId(productId));
    }

    @Override
    public List<ProductRatingDTO> findProductRatingByCustomerId(final String customerId) {
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
