package com.example.currency.controller;

import com.example.currency.CurrencyApplication;
import com.example.currency.dto.CoinDeskTransferDto;
import com.example.currency.dto.HttpResponseEntity;
import com.example.currency.entity.CoinDeskEntity;
import com.example.currency.entity.CurrencyEntity;
import com.example.currency.service.TransferService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(
    classes = CurrencyApplication.class)
@AutoConfigureMockMvc
public class CoindeskControllerTest {


    private MockMvc mockMvc;
    @MockBean
    private TransferService transferService;

    @Autowired
    private CoindeskController coindeskController;


    private static ObjectMapper mapper = new ObjectMapper();

    private CoinDeskEntity coinDeskEntity = new CoinDeskEntity();

    @Before
    public void setup() throws Exception {
        this.mockMvc = standaloneSetup(this.coindeskController).build();
        CurrencyEntity currency = new CurrencyEntity();
        currency.setName("美金");
        currency.setCode("USD");
        currency.setRate("24,789.0217");
        currency.setRate_float(20713.5082);

        Map<String, CurrencyEntity> map = new HashMap<>();
        map.put("USD", currency);

        coinDeskEntity = new CoinDeskEntity();
        coinDeskEntity.setChartName("Bitcoin");
        coinDeskEntity.setBpi(map);
    }

    @Test
    public void testGetCoinDesk() throws Exception {
        Mockito.when(transferService.getCoinDeskItems()).thenReturn(HttpResponseEntity.ok(coinDeskEntity));

        mockMvc.perform(get("/coindesk")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print())
            .andExpect(jsonPath("$.data", Matchers.anything()));

    }

    @Test
    public void testGetCoinDeskTransferAPI() throws Exception {

        CoinDeskTransferDto coinDeskTransferDto = new CoinDeskTransferDto();
        coinDeskTransferDto.setCurrency(coinDeskEntity.getBpi());

        Mockito.when(transferService.getCoinDeskTransfer()).thenReturn(HttpResponseEntity.ok(coinDeskTransferDto));

        mockMvc.perform(get("/coindesk/transfer")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print())
            .andExpect(jsonPath("$.data", Matchers.anything()));

    }
}
