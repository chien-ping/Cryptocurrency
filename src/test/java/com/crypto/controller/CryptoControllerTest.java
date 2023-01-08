package com.crypto.controller;

import com.crypto.service.CryptoSource.ICrypto;
import com.crypto.dto.CryptoRateDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql({"/sql/currency-test-data.sql"})
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CryptoControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    ICrypto bitcoinStub;

    /**
     * 呼叫資料轉換的API查詢匯率
     * @throws Exception
     */
    @Test
    public void getExchangeRate_success() throws Exception {
        String cryptoStr = "{\n" +
                "    \"cryptoName\": \"Bitcoin\",\n" +
                "    \"updatedTime\": \"1111/11/11 11:11:11\",\n" +
                "    \"exchangeRates\": [\n" +
                "        {\n" +
                "            \"code\": \"USD\",\n" +
                "            \"rateFormat\": \"16,914.9997\",\n" +
                "            \"rate\": 16915.0\n" +
                "        },\n" +
                "        {\n" +
                "            \"code\": \"GBP\",\n" +
                "            \"rateFormat\": \"14,134.0384\",\n" +
                "            \"rate\": 14134.038\n" +
                "        },\n" +
                "        {\n" +
                "            \"code\": \"EUR\",\n" +
                "            \"rateFormat\": \"16,477.6792\",\n" +
                "            \"rate\": 16477.68\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        ObjectMapper om = new ObjectMapper();
        CryptoRateDto vo = om.readValue(cryptoStr, CryptoRateDto.class);
        when(bitcoinStub.getRateData()).thenReturn(vo);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/v1/crypto/{crypto_name:bitcoin}", "bitcoin");

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk());
    }
}
