package com.generic.khatabook.controller;

import com.generic.khatabook.service.CustomerService;
import com.generic.khatabook.service.IdGeneratorService;
import com.generic.khatabook.service.KhatabookService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = WavefrontProperties.Application.class)
@AutoConfigureMockMvc
//@TestPropertySource(locations = "classpath:application-integrationtest.properties")
@WebMvcTest(value = KhatabookController.class)
class KhatabookControllerTest {


    @Autowired
    private MockMvc mvc;
    @MockBean
    private KhatabookController khatabookController;

    @MockBean
    private KhatabookService myKhatabookService;
    @MockBean
    private CustomerService myCustomerService;
    @MockBean
    private IdGeneratorService myIdGeneratorService;

    @Test
    void testAllKhatabook() throws Exception {
        mvc.perform(get("/khatabook/khatabooks").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(
                (ResultMatcher) content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
//           .andExpect(ResultMatcher);
    }

}