package com.generic.khatabook.specification.services.impl;

import com.generic.khatabook.specification.entity.Product;
import com.generic.khatabook.specification.model.ProductDTO;
import com.generic.khatabook.specification.model.ProductRatingDTO;
import com.generic.khatabook.specification.repository.ProductManagementRepository;
import com.generic.khatabook.specification.services.ProductManagementService;
import com.generic.khatabook.specification.services.mapper.ProductManagementMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProductManagementServiceImpl implements ProductManagementService {

    private ProductManagementRepository myProductManagementRepository;
    private ProductManagementMapper myProductManagementMapper;

    @Autowired
    public ProductManagementServiceImpl(final ProductManagementRepository thatProductManagementRepository, final ProductManagementMapper productManagementMapper) {
        this.myProductManagementRepository = thatProductManagementRepository;
        myProductManagementMapper = productManagementMapper;
    }


    @Override
    public List<ProductDTO> getAllProducts() {
        return myProductManagementMapper.mapToDTOs(myProductManagementRepository.findAll());
    }

    @Override
    public List<ProductDTO> findProductByName(final String productName) {
        return myProductManagementMapper.mapToDTOs(myProductManagementRepository.findAll(Example.of(Product.builder().name(productName).build())));
    }

    @Override
    public Product saveProduct(final ProductDTO product) {

        return myProductManagementRepository.save(myProductManagementMapper.mapToEntity(product));
    }

    @Override
    public ProductDTO findProductById(final String productId) {
        return myProductManagementMapper.mapToDTO(myProductManagementRepository.findById(productId).orElse(null));
    }

    @Override
    public void delete(final ProductDTO productDTO) {
        myProductManagementRepository.delete(myProductManagementMapper.mapToEntity(productDTO));
    }

    @Override
    public ProductDTO updateProduct(final ProductDTO product) {
        return myProductManagementMapper.mapToDTO(myProductManagementRepository.save(myProductManagementMapper.mapToEntity(product)));
    }

    @Override
    public void saveProductRating(final ProductRatingDTO productRatingDTO) {
        log.info(productRatingDTO + " saving.");
    }
}
