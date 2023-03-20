package com.generic.khatabook.specification.services.impl;

import com.generic.khatabook.specification.entity.Product;
import com.generic.khatabook.specification.model.ProductDTO;
import com.generic.khatabook.specification.model.ProductRatingDTO;
import com.generic.khatabook.specification.repository.ProductManagementRepository;
import com.generic.khatabook.specification.services.ProductManagementService;
import com.generic.khatabook.specification.services.mapper.ProductMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProductManagementServiceImpl implements ProductManagementService {

    private ProductManagementRepository myProductManagementRepository;
    private ProductMapper myProductMapper;

    @Autowired
    public ProductManagementServiceImpl(final ProductManagementRepository thatProductManagementRepository, final ProductMapper productMapper) {
        this.myProductManagementRepository = thatProductManagementRepository;
        myProductMapper = productMapper;
    }


    @Override
    public List<ProductDTO> getAllProducts() {
        return myProductMapper.mapToDTOs(myProductManagementRepository.findAll());
    }

    @Override
    public List<ProductDTO> findProductByName(final String productName) {
        return myProductMapper.mapToDTOs(myProductManagementRepository.findAll(Example.of(Product.builder().name(productName).build())));
    }

    @Override
    public ProductDTO saveProduct(final ProductDTO product) {

        return myProductMapper.mapToDTO(myProductManagementRepository.save(myProductMapper.mapToEntity(product)));
    }

    @Override
    public ProductDTO findProductById(final String productId) {
        return myProductMapper.mapToDTO(myProductManagementRepository.findById(productId).orElse(null));
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
    }
}
