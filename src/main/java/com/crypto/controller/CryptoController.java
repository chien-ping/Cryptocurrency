package com.crypto.controller;

import com.crypto.dto.CurrencyDto;
import com.crypto.service.CryptoService;
import com.crypto.service.CurrencyService;
import com.crypto.dto.CryptoRateDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * 虛擬貨幣處理
 */
@Slf4j
@RequestMapping("/v1/crypto")
@RestController
public class CryptoController {

    @Autowired
    CryptoService cryptoService;
    @Autowired
    CurrencyService currencyService;

    /**
     * 查詢虛擬貨幣兌換匯率
     * @param cryptoName 貨幣名稱
     * @return 貨幣資訊
     */
    @GetMapping(value = "/{crypto_name:bitcoin}")
    public CryptoRateDto getExchangeRate(@PathVariable("crypto_name")String cryptoName) {
        CryptoRateDto coinRate = cryptoService.getExchangeRate(cryptoName);
        Map<String, String> nameMapping = currencyService.query(new CurrencyDto()).stream()
                .collect(Collectors.toMap(c -> c.getCode(), c-> c.getNameZh()));
        coinRate.getExchangeRates().stream()
                .forEach(r -> r.setNameZh(nameMapping.get(r.getCode())));

        return coinRate;
    }
}
