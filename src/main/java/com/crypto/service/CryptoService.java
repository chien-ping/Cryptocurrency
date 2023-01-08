package com.crypto.service;

import com.crypto.service.CryptoSource.ICrypto;
import com.crypto.dto.CryptoRateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 * 虛擬貨幣幣處理
 */
@Service
public class CryptoService {
    @Autowired
    ApplicationContext context;

    /**
     * 查詢兌換匯率
     * @param cryptoName 貨幣名稱
     * @return 匯率資訊
     */
    public CryptoRateDto getExchangeRate(String cryptoName) {
        ICrypto resource = (ICrypto)context.getBean(cryptoName);
        return  resource.getRateData();
    }
}
