package com.crypto.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * 對應幣別匯率
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExchangeRateDto {

    /**
     * 貨幣代碼
     */
    private String code;

    /**
     * 貨幣中文名稱
     */
    private String nameZh;

    /**
     * 匯率
     */
    private String rateFormat;

    /**
     * 匯率
     */
    private Float rate;

}
