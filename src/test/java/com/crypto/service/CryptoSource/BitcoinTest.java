package com.crypto.service.CryptoSource;

import com.crypto.dto.CryptoRateDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class BitcoinTest {

    @Autowired
    Bitcoin bitcoin;

    /**
     * 呼叫coindesk API取得匯率資訊
     */
    @Test
    public void getRateDataFromOuter(){
        CryptoRateDto vo = bitcoin.getRateData();
        assertNotNull(vo);
        assertEquals(vo.getCryptoName(), "Bitcoin");
    }
}
