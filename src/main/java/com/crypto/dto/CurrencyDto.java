package com.crypto.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * 幣別資料
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CurrencyDto {

    /**
     * 幣別代碼
     */
    @NotEmpty(message = "{validation.currency.code.not_empty}")
    @Pattern(regexp = "^[a-zA-Z]{3}$", message = "{validation.currency.code.pattern}")
    private String code;

    /**
     * 幣別中文名稱
     */
    @NotEmpty(message = "{validation.currency.name.not_empty}")
    @Size(max = 30, message = "{validation.name.length_limit_30}")
    private String nameZh;

    /**
     * 設定幣別代碼
     */
    public void setCode(String code){
        this.code = code.toUpperCase();
    }

    /**
     * 設定幣別名稱
     */
    public void setNameZh(String nameZh){
        this.nameZh = nameZh.trim();
    }

}
