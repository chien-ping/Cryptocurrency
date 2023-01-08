package com.crypto.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

/**
 * 比特幣匯率資料
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BitcoinVo {

    /**
     * 更新時間
     */
     private BitcoinVo.TimeVo time;

    /**
     * 圖表名稱
     */
     private String chartName;

    /**
     * 對應匯率
     */
    private Map<String, BitcoinVo.RateVo> bpi;

    /**
     * 更新時間
     */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TimeVo {

        /**
         * 更新時間- UTC
         */
        private String updated;

        /**
         * 更新時間-ISO
         */
        private String updatedISO;

    }

    /**
     * 匯率資訊
     */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class RateVo {

        /**
         * 幣別代碼
         */
        private String code;

        /**
         * 貨幣符號
         */
        private String symbol;

        /**
         * 匯率
         */
        private String rate;

        /**
         * 匯率
         */
        @JsonProperty("rate_float")
        private Float rateFloat;

    }
}
