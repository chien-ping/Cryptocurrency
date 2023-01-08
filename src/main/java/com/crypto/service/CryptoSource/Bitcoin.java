package com.crypto.service.CryptoSource;

import com.crypto.vo.BitcoinVo;
import com.crypto.dto.CryptoRateDto;
import com.crypto.convertor.CryptoConvertor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


/**
 * 比特幣資料來源處理
 */
@Slf4j
@Component("bitcoin")
public class Bitcoin implements ICrypto {

    @Value("${coindesk.bitcoin.rate.url}")
    private String rateUrl;

    @Autowired
    RestTemplate restTemplate;

    /**
     * 取得匯率資料
     * @return 貨幣資訊
     */
    @Override
    public CryptoRateDto getRateData(){
        ResponseEntity<String> response = restTemplate
                .exchange(rateUrl, HttpMethod.GET, null, String.class);
        log.info("coindesk response: status: {}, body: {}" , response.getStatusCode(), response.getBody());
        ObjectMapper om = new ObjectMapper();
        BitcoinVo bitcoinVo = null;
        try{
            bitcoinVo = om.readValue(response.getBody(), BitcoinVo.class);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
        return  CryptoConvertor.INSTANCE.toDto(bitcoinVo);
    }
}
