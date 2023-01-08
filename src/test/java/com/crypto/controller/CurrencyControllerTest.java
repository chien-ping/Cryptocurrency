package com.crypto.controller;

import com.crypto.model.Currency;
import com.crypto.model.CurrencyHistory;
import com.crypto.repository.CurrencyHistoryRepository;
import com.crypto.repository.CurrencyRepository;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql({"/sql/currency-test-data.sql"})
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CurrencyControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    CurrencyRepository currencyRepository;
    @Autowired
    CurrencyHistoryRepository currencyHistoryRepository;

    /**
     * 查詢幣別對應表資料API
     * @throws Exception
     */
    @Test
    public void getAllCurrencies_withData() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/v1/currency");

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].code",
                        equalTo("EUR")))
                .andExpect(jsonPath("$[0].nameZh",
                        equalTo("歐元")));
    }

    /**
     * 透過幣別對應表資料API新增幣別
     * @throws Exception
     */
    @Test
    public void addCurrency_success() throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        JSONObject request = new JSONObject()
                .put("code", "NTD")
                .put("nameZh", "新台幣");

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/v1/currency")
                .headers(httpHeaders)
                .content(request.toString());

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk());
        assertEquals(currencyRepository.findById("NTD").isPresent(), true);

    }


    /**
     * 透過幣別對應表資料API更新幣別
     * @throws Exception
     */
    @Test
    public void updateCurrency_success() throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        JSONObject request = new JSONObject()
                .put("code", "JPY")
                .put("nameZh", "日圓");

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .patch("/v1/currency/{currency_code}","JPY")
                .headers(httpHeaders)
                .content(request.toString());

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk());
        Optional<Currency> eurOpt = currencyRepository.findById("JPY");
        assertEquals(eurOpt.isPresent(), true);
        Currency eur = eurOpt.get();
        assertEquals(eur.getNameZh(), "日圓");
        assertNotNull(eur.getModifiedDate());
    }

    /**
     * 透過幣別對應表資料API刪除幣別
     * @throws Exception
     */
    @Test
    public void deleteCurrency_success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/v1/currency/{currency_code}", "UZZ");

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk());
        assertEquals(currencyRepository.findById("UZZ").isPresent(), false);
        CurrencyHistory uzz = new CurrencyHistory();
        uzz.setCode("UZZ");
        List<CurrencyHistory> list = currencyHistoryRepository.findAll(Example.of(uzz));
        assertThat(list.size(), greaterThan(0));
        assertNotNull(list.get(0).getDeletedDate());
    }
}
