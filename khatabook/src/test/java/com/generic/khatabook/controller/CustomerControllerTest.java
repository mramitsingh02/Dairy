package com.generic.khatabook.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.generic.khatabook.exchanger.SpecificationClient;
import com.generic.khatabook.model.CustomerDTO;
import com.generic.khatabook.service.CustomerService;
import com.generic.khatabook.service.IdGeneratorService;
import com.generic.khatabook.service.KhatabookService;
import com.generic.khatabook.service.PaymentService;
import com.generic.khatabook.validator.CustomerValidation;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EntityManagerFactory myEntityManagerFactory;
    @MockBean
    private CustomerService customerService;

    @MockBean
    private KhatabookService khatabookService;
    @MockBean
    private PaymentService paymentService;

    @MockBean
    private SpecificationClient mySpecificationClient;

    @MockBean
    private IdGeneratorService myIdGeneratorService;

    @MockBean
    private CustomerValidation myCustomerValidation;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee() throws Exception {

        // given - precondition or setup
        CustomerDTO customerDTO = CustomerDTO.of(null, "Khatabook1", "msisdn", "Amit", "Singh", null, null);
        given(customerService.saveAndUpdate(any(CustomerDTO.class))).willAnswer((invocation) -> invocation.getArgument(0));

        // when - action or behaviour that we are going test
        ResultActions response = mockMvc.perform(post("/khatabook/Khatabook1/customer").contentType(MediaType.APPLICATION_JSON).content(
                objectMapper.writeValueAsString(customerDTO)));

        // then - verify the result or output using assert statements
        response.andDo(print()).andExpect(status().isCreated()).andExpect(jsonPath("$.firstName",
                                                                                   is(customerDTO.firstName()))).andExpect(
                jsonPath("$.lastName", is(customerDTO.lastName())));

    }

}