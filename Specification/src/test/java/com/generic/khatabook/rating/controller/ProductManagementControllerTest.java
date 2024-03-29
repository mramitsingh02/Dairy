package com.generic.khatabook.rating.controller;

import com.generic.khatabook.common.exceptions.AppEntity;
import com.generic.khatabook.common.exceptions.SubEntity;
import com.generic.khatabook.common.model.Container;
import com.generic.khatabook.common.model.ProductDTO;
import com.generic.khatabook.common.model.ProductRatingDTO;
import com.generic.khatabook.common.model.ProductUpdatable;
import com.generic.khatabook.common.model.RatingDTO;
import com.generic.khatabook.common.model.UnitOfMeasurement;
import com.generic.khatabook.rating.services.ProductManagementService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@AutoConfigureJsonTesters
@SpringBootTest
@AutoConfigureMockMvc
class ProductManagementControllerTest extends AbstractTest {
    @MockBean
    private ProductManagementService myProductManagementService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetProducts() throws Exception {
        String path = "/products";
        List<ProductDTO> listOfProducts = getProductDTOS();
        when(myProductManagementService.findAllProducts()).thenReturn(listOfProducts);

        mockMvc.perform(get(path).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(jsonPath("$.products").exists()).andExpect(jsonPath("$.products[*].id").isNotEmpty()).andExpect(jsonPath("$.products[*].name").isNotEmpty()).andExpect(jsonPath("$.products[*].quantity").isNotEmpty()).andExpect(jsonPath("$.products[*].price").isNotEmpty()).andExpect(jsonPath("$.products[*].unitOfMeasurement").isNotEmpty()).andExpect(jsonPath("$.products[*].rating").isNotEmpty());
    }

    private static List<ProductDTO> getProductDTOS() {
        List<ProductDTO> listOfProducts = new ArrayList<>();
        listOfProducts.add(new ProductDTO("1", "Milk", 1, BigDecimal.TEN, UnitOfMeasurement.LITTER, 3.0f));
        listOfProducts.add(new ProductDTO("2", "Unit", 1, BigDecimal.TEN, UnitOfMeasurement.READING, 4.0f));
        listOfProducts.add(new ProductDTO("3", "Room", 1, BigDecimal.TEN, UnitOfMeasurement.ITEM, 2.0f));
        listOfProducts.add(new ProductDTO("4", "Cash", 1, BigDecimal.TEN, UnitOfMeasurement.ITEM, 5.0f));
        listOfProducts.add(new ProductDTO("5", "ShowRoom", 1, BigDecimal.TEN, UnitOfMeasurement.ITEM, 1.0f));
        return listOfProducts;
    }

    @Test
    void testGetProduct() throws Exception {
        String prodId = "1";
        String path = "/product/%s".formatted(prodId);
        Container<ProductDTO, ProductUpdatable> prods = getProductDTOS().stream().filter(x -> x.id().equals(prodId)).map(x -> new Container(x, x.updatable())).findFirst().orElse(null);
        when(myProductManagementService.findProductById(prodId)).thenReturn(prods);


        mockMvc.perform(get(path).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(jsonPath("$.id").exists()).andExpect(jsonPath("$.name").exists()).andExpect(jsonPath("$.quantity").exists()).andExpect(jsonPath("$.price").exists()).andExpect(jsonPath("$.unitOfMeasurement").exists()).andExpect(jsonPath("$.rating").exists());
    }

    @Test
    void testGetNonExistingProductById() throws Exception {
        String prodId = "1";
        String path = "/product/%s".formatted(prodId);
        when(myProductManagementService.findProductById(prodId)).thenReturn(Container.empty());
        mockMvc.perform(get(path).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
    }

    @Test
    void testSearchByProductName() throws Exception {
        String prodName = "milk";
        String path = "/product/?name=%s".formatted(prodName);
        List<ProductDTO> listOfProducts = getProductDTOS();
        final List<ProductDTO> prods = listOfProducts.stream().filter(x -> x.name().equalsIgnoreCase(prodName)).collect(Collectors.toList());
        when(myProductManagementService.findProductByName(prodName)).thenReturn(prods);
        mockMvc.perform(get(path).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(jsonPath("$.products").exists()).andExpect(jsonPath("$.products[*].id").isNotEmpty()).andExpect(jsonPath("$.products[*].name").isNotEmpty()).andExpect(jsonPath("$.products[*].quantity").isNotEmpty()).andExpect(jsonPath("$.products[*].price").isNotEmpty()).andExpect(jsonPath("$.products[*].unitOfMeasurement").isNotEmpty()).andExpect(jsonPath("$.products[*].rating").isNotEmpty());
    }

    @Test
    void testSearchByProductUnitOfMeasurement() throws Exception {
        String prodUnitOfMeasurement = "item";
        String path = "/product/?unitOfMeasurement=%s".formatted(prodUnitOfMeasurement);
        List<ProductDTO> listOfProducts = getProductDTOS();
        final List<ProductDTO> prods =
                listOfProducts.stream().filter(x -> x.unitOfMeasurement().equals(UnitOfMeasurement.valueOf(prodUnitOfMeasurement.toUpperCase()))).collect(Collectors.toList());
        when(myProductManagementService.findProductByUnitOfMeasurement(prodUnitOfMeasurement)).thenReturn(prods);
        mockMvc.perform(get(path).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(jsonPath("$.products").exists()).andExpect(jsonPath("$.products[*].id").isNotEmpty()).andExpect(jsonPath("$.products[*].name").isNotEmpty()).andExpect(jsonPath("$.products[*].quantity").isNotEmpty()).andExpect(jsonPath("$.products[*].price").isNotEmpty()).andExpect(jsonPath("$.products[*].unitOfMeasurement").isNotEmpty()).andExpect(jsonPath("$.products[*].rating").isNotEmpty());
    }

    @Test
    void testSearchNonExistingProductUnitOfMeasurement() throws Exception {
        String prodUnitOfMeasurement = "ites";
        String path = "/product/?unitOfMeasurement=%s".formatted(prodUnitOfMeasurement);
        List<ProductDTO> listOfProducts = getProductDTOS();
        final List<ProductDTO> prods =
                listOfProducts.stream().filter(x -> x.unitOfMeasurement().equals(prodUnitOfMeasurement)).collect(Collectors.toList());
        when(myProductManagementService.findProductByUnitOfMeasurement(prodUnitOfMeasurement)).thenThrow(new com.generic.khatabook.common.exceptions.IllegalArgumentException(AppEntity.PRODUCT, SubEntity.UNIT_OF_MEASUREMENT, "Not Found"));
        mockMvc.perform(get(path).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotAcceptable());
    }

    @Test
    void testGetByProductNameNotFound() throws Exception {
        String prodName = "milksake";
        String path = "/product/?name=%s".formatted(prodName);
        when(myProductManagementService.findProductByName(prodName)).thenReturn(null);
        mockMvc.perform(get(path).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
    }

    @Test
    void testGetAllProductQueryNotPassed() throws Exception {
        List<ProductDTO> listOfProducts = getProductDTOS();
        when(myProductManagementService.findAllProducts()).thenReturn(listOfProducts);
        mockMvc.perform(get("/product/").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(jsonPath("$.products").exists()).andExpect(jsonPath("$.products[*].id").isNotEmpty()).andExpect(jsonPath("$.products[*].name").isNotEmpty()).andExpect(jsonPath("$.products[*].quantity").isNotEmpty()).andExpect(jsonPath("$.products[*].price").isNotEmpty()).andExpect(jsonPath("$.products[*].unitOfMeasurement").isNotEmpty()).andExpect(jsonPath("$.products[*].rating").isNotEmpty());
    }

    @Test
    void testCreateProduct() throws Exception {
        String prodId = "1";
        String path = "/product";
        ProductDTO milkProd = getProductDTOS().get(0);
        when(myProductManagementService.saveProduct(milkProd)).thenReturn(milkProd);
        mockMvc.perform(post(path).contentType(MediaType.APPLICATION_JSON).content(mapToJson(milkProd))).andExpect(status().isCreated());
    }

    @Test
    void testRatingExistingProduct() throws Exception {
        String prodId = "1";
        String path = "/product/%s/rating".formatted(prodId);
        RatingDTO productRatingDTO =
                new RatingDTO(null, prodId, prodId, 4.0f, "Good milk product.");

        Container<ProductDTO, ProductUpdatable> prods = getProductDTOS().stream().filter(x -> x.id().equals(prodId)).map(x -> new Container(x, x.updatable())).findFirst().orElse(null);
        when(myProductManagementService.findProductById(prodId)).thenReturn(prods);
        doNothing().when(myProductManagementService).saveProductRating(productRatingDTO);
        mockMvc.perform(post(path).contentType(MediaType.APPLICATION_JSON).content(mapToJson(productRatingDTO))).andExpect(status().isCreated());
    }

    @Test
    void testRatingNonExistingProduct() throws Exception {
        String prodId = "113";
        String path = "/product/%s/rating".formatted(prodId);
        List<ProductDTO> listOfProducts = getProductDTOS();
        Container<ProductDTO, ProductUpdatable> prods =
                listOfProducts.stream().filter(x -> x.id().equals(prodId)).map(x -> new Container(x, x.updatable())).findFirst().orElse(Container.empty());
        when(myProductManagementService.findProductById(prodId)).thenReturn(prods);
        ProductRatingDTO productRatingDTO =
                new ProductRatingDTO(null, prodId, prodId, 4.0f, "Good milk product.");
        mockMvc.perform(post(path).contentType(MediaType.APPLICATION_JSON).content(mapToJson(productRatingDTO))).andExpect(status().isNotFound());
    }

    @Test
    void testCreateProducts() throws Exception {

        mockMvc.perform(post("/products").contentType(MediaType.APPLICATION_JSON).content(mapToJson(getProductDTOS()))).andExpect(status().isAccepted());
    }

    @Test
    void testDeleteProductById() throws Exception {
        String prodId = "1";
        String path = "/product/%s".formatted(prodId);
        List<ProductDTO> listOfProducts = getProductDTOS();
        Container<ProductDTO, ProductUpdatable> prods = listOfProducts.stream().filter(x -> x.id().equals(prodId)).map(x -> new Container(x, x.updatable())).findFirst().orElse(null);
        when(myProductManagementService.findProductById(prodId)).thenReturn(prods);
        mockMvc.perform(delete(path).contentType(MediaType.APPLICATION_JSON).content(mapToJson(getProductDTOS()))).andExpect(status().isOk());
        verify(myProductManagementService).delete(any(ProductDTO.class));
    }

    @Test
    void testDeleteNotExistingProductById() throws Exception {
        String prodId = "113";
        String path = "/product/%s".formatted(prodId);
        List<ProductDTO> listOfProducts = getProductDTOS();
        Container<ProductDTO, ProductUpdatable> prods =
                listOfProducts.stream().filter(x -> x.id().equals(prodId)).map(x -> new Container(x, x.updatable())).findFirst().orElse(Container.empty());
        when(myProductManagementService.findProductById(prodId)).thenReturn(prods);
        mockMvc.perform(delete(path).contentType(MediaType.APPLICATION_JSON).content(mapToJson(getProductDTOS()))).andExpect(status().isNotFound());
        verify(myProductManagementService, never()).delete(any(ProductDTO.class));
    }

    @Test
    void testUpdateNotExistProduct() throws Exception {
        String prodId = "113";
        String path = "/product/%s".formatted(prodId);
        ProductDTO milkProd = new ProductDTO("1", "Milk",
                1, BigDecimal.valueOf(50),
                UnitOfMeasurement.LITTER, 4.0f);
        Container<ProductDTO, ProductUpdatable> prods =
                getProductDTOS().stream().filter(x -> x.id().equals(prodId)).map(x -> new Container(x, x.updatable())).findFirst().orElse(Container.empty());
        when(myProductManagementService.findProductById(prodId)).thenReturn(prods);
        mockMvc.perform(put(path).contentType(MediaType.APPLICATION_JSON).content(mapToJson(milkProd))).andExpect(status().isNotFound());
        verify(myProductManagementService, never()).delete(any(ProductDTO.class));
    }

    @Test
    void testUpdateProduct() throws Exception {
        String prodId = "1";
        String path = "/product/%s".formatted(prodId);
        ProductDTO milkProd = new ProductDTO("1", "Milk",
                1, BigDecimal.valueOf(50),
                UnitOfMeasurement.LITTER, 4.0f);
        Container<ProductDTO, ProductUpdatable> prods =
                getProductDTOS().stream().filter(x -> x.id().equals(prodId)).map(x -> new Container(x, x.updatable())).findFirst().orElse(Container.empty());
        when(myProductManagementService.findProductById(prodId)).thenReturn(prods);
        when(myProductManagementService.updateProduct(milkProd)).thenReturn(milkProd);
        mockMvc.perform(put(path)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapToJson(milkProd)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.quantity").exists())
                .andExpect(jsonPath("$.price").exists())
                .andExpect(jsonPath("$.price").value(BigDecimal.valueOf(50)))
                .andExpect(jsonPath("$.unitOfMeasurement").exists())
                .andExpect(jsonPath("$.rating").exists())
                .andExpect(jsonPath("$.rating").value(4.0f));
        ;
    }

    @Test
    void testCreateProductsWithNullPayload() throws Exception {
        mockMvc.perform(post("/products").contentType(MediaType.APPLICATION_JSON).content("[]")).andExpect(status().isNoContent());
    }
}