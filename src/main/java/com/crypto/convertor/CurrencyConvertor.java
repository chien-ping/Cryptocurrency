package com.crypto.convertor;

import com.crypto.model.Currency;
import com.crypto.model.CurrencyHistory;
import com.crypto.dto.CurrencyDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 幣別物件轉換
 */
@Mapper
public interface CurrencyConvertor {

    CurrencyConvertor INSTANCE = Mappers.getMapper( CurrencyConvertor.class );

    CurrencyDto toDto(Currency dto);

    Currency toModel(CurrencyDto dto);

    CurrencyHistory toHistory(Currency currency);
}
