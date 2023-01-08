package com.crypto.service;

import com.crypto.exception.ValidationException;
import com.crypto.model.Currency;
import com.crypto.model.CurrencyHistory;
import com.crypto.repository.CurrencyHistoryRepository;
import com.crypto.repository.CurrencyRepository;
import com.crypto.dto.CurrencyDto;
import com.crypto.convertor.CurrencyConvertor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 幣別資訊處理
 */
@Slf4j
@Service
public class CurrencyService {

    @Autowired
    CurrencyRepository currencyRepository;
    @Autowired
    CurrencyHistoryRepository currencyHistoryRepository;

    /**
     * 查詢幣別資料
     * @param currency 查詢條件
     * @return 幣別列表
     */
    public List<CurrencyDto> query(CurrencyDto currency) {
        Currency condition = CurrencyConvertor.INSTANCE.toModel(currency);
        List<Currency> currencies = currencyRepository.findAll(Example.of(condition), Sort.by("code"));
        return currencies.stream()
                .map(c -> CurrencyConvertor.INSTANCE.toDto(c))
                .collect(Collectors.toList());
    }

    /**
     * 新增幣別
     * @param currency 幣別資料
     */
    public void add(CurrencyDto currency) {
        currencyRepository.findById(currency.getCode())
                .ifPresent(c -> { throw new ValidationException("validation.currency.code.already_exist",
                            "Add currency fail : code already exists : " + currency.getCode());});

        Currency newCurrency = CurrencyConvertor.INSTANCE.toModel(currency);
        newCurrency.setCreatedDate(new Date());
        currencyRepository.save(newCurrency);
    }

    /**
     * 更新幣別
     * @param currency 幣別資料
     */
    public void update(CurrencyDto currency) {
        Currency updCurrency = currencyRepository.findById(currency.getCode())
                .orElseThrow(() -> new ValidationException("validation.currency.code.not_exist",
                        "Update currency fail : code does not exists : " + currency.getCode()));

        updCurrency.setNameZh(currency.getNameZh());
        currencyRepository.save(updCurrency);
    }

    /**
     * 刪除幣別
     * @param currency 幣別資料
     */
    @Transactional
    public void delete(CurrencyDto currency) {
        Currency delCurrency = currencyRepository.findById(currency.getCode())
                .orElseThrow(() -> new ValidationException("validation.currency.code.not_exist",
                        "Delete currency fail : code does not exists : " + currency.getCode()));

        CurrencyHistory history = CurrencyConvertor.INSTANCE.toHistory(delCurrency);
        history.setDeletedDate(new Date());
        currencyRepository.delete(delCurrency);
        currencyHistoryRepository.save(history);
    }
}
