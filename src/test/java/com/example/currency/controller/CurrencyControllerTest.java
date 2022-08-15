package com.example.currency.controller;

import com.example.currency.CurrencyApplication;
import com.example.currency.entity.Currency;
import com.example.currency.service.CurrencyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CurrencyApplication.class)
public class CurrencyControllerTest {

    private MockMvc mockMvc;
    @MockBean
    private CurrencyService currencyService;

    @Autowired
    private CurrencyController currencyController;

    private List<Currency> currencies;

    private static ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setup() throws Exception {
        this.mockMvc = standaloneSetup(this.currencyController).build();
        Currency currency = new Currency();
        currency.setCountryCode("USD");
        currency.setCountryName("美金");
        currencies = Arrays.asList(currency);
    }

    @Test
    public void testGetAPI() throws Exception {

        Mockito.when(currencyService.getCurrency()).thenReturn(currencies);
        mockMvc.perform(get("/currency"))
            .andExpect(status().isOk())
            .andDo(print())
            .andExpect(jsonPath("$.data", Matchers.hasSize(1)))
            .andExpect(jsonPath("$.data[0].countryName", Matchers.equalTo("美金")));

    }

    @Test
    public void testPostAPI() throws Exception {

        String json = mapper.writeValueAsString(currencies);
        mockMvc.perform(post("/currency")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .characterEncoding("utf-8"))
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn();
    }

    @Test
    public void testPutAPI() throws Exception {
        Currency currency = new Currency();
        currency.setCountryCode("USD");
        currency.setCountryName("美金");
        Mockito.when(currencyService.saveCurrency(ArgumentMatchers.any())).thenReturn(currency);
        String json = mapper.writeValueAsString(currency);

        mockMvc.perform(put("/currency").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                .content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
            .andDo(print())
            .andExpect(jsonPath("$.data.countryCode", Matchers.equalTo("USD")))
            .andExpect(jsonPath("$.data.countryName", Matchers.equalTo("美金")));
    }

    @Test
    public void testDeleteAPI() throws Exception {
        Mockito.when(currencyService.deleteCurrency(ArgumentMatchers.anyString())).thenReturn(Boolean.valueOf("Student is deleted"));

        MvcResult requestResult = mockMvc.perform(delete("/currency").param("code", "USD"))
            .andExpect(status().isOk()).andDo(print()).andReturn();
        String result = requestResult.getResponse().getContentAsString();
        assertEquals(result, "{\"success\":false,\"errorMsg\":\"item not existing\",\"data\":null,\"total\":null}");
    }
}
