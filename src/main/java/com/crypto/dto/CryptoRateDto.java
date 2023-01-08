package com.crypto.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

/**
 * 匯率資訊
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CryptoRateDto {

    /**
     * 貨幣名稱
     */
    private String cryptoName;

    /**
     * 更新時間
     */
    private String updatedTime;

    /**
     * 對應幣別匯率
     */
    private List<ExchangeRateDto> exchangeRates;



}
