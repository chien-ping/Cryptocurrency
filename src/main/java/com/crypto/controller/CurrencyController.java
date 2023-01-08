package com.crypto.controller;

import com.crypto.service.CurrencyService;
import com.crypto.dto.CurrencyDto;
import com.crypto.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Locale;

/**
 * 幣別維護
 */
@Slf4j
@RequestMapping("/v1/currency")
@RestController
public class CurrencyController {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private CurrencyService currencyService;

    /**
     * 查詢貨幣列表
     * @param code 幣別代碼
     * @return 幣別資訊
     */
    @GetMapping(value = "")
    public List<CurrencyDto> getCurrencies(String code) {
        CurrencyDto condition = new CurrencyDto();
        if(StringUtils.hasLength(code))
            condition.setCode(code);
        List<CurrencyDto> currencies = currencyService.query(condition);
        return currencies;
    }

    /**
     * 查詢特定貨幣
     * @param code 幣別代碼
     * @return 幣別資訊
     */
    @GetMapping(value = "/{currency_code}")
    public CurrencyDto getCurrency(@PathVariable("currency_code")String code) {
        List<CurrencyDto> currencies = getCurrencies(code);
        if(currencies.size() > 0)
            return currencies.get(0);
        else
            return new CurrencyDto();

    }

    /**
     * 新增幣別
     * @param currency 幣別資料
     * @return 執行結果
     */
    @PostMapping(value="")
    public ResponseVo addCurrency(@Valid @RequestBody CurrencyDto currency) {
        log.warn("Add currency : {}", currency.toString());
        currencyService.add(currency);
        return new ResponseVo(messageSource.getMessage("execution.success", null, Locale.getDefault()));
    }

    /**
     * 更新幣別
     * @param currency 幣別資料
     * @return 執行結果
     */
    @PatchMapping(value="/{currency_code}")
    public ResponseVo updateCurrencies(@Valid @RequestBody CurrencyDto currency) {
        log.warn("Update currency : {}", currency.toString());
        currencyService.update(currency);
        return new ResponseVo(messageSource.getMessage("execution.success", null, Locale.getDefault()));
    }


    /**
     * 刪除幣別
     * @param code 幣別代碼
     * @return 執行結果
     */
    @DeleteMapping(value="/{currency_code}")
    public ResponseVo deleteCurrency(@NotEmpty @PathVariable("currency_code")String code) {
        log.warn("Delete currency : {}", code);
        CurrencyDto currency = new CurrencyDto();
        currency.setCode(code);
        currencyService.delete(currency);
        return new ResponseVo(messageSource.getMessage("execution.success", null, Locale.getDefault()));
    }

}
