package com.generic.khatabook.rating.controller;

import com.generic.khatabook.rating.services.RatingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureJsonTesters
@SpringBootTest
@AutoConfigureMockMvc
class ProductManagementControllerTest extends AbstractTest {
    @MockBean
    private RatingService myRatingService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testCreateProductsWithNullPayload() throws Exception {
        mockMvc.perform(post("/products").contentType(MediaType.APPLICATION_JSON).content("[]")).andExpect(status().isNoContent());
    }
}