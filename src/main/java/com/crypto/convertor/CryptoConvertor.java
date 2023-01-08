package com.crypto.convertor;

import com.crypto.vo.BitcoinVo;
import com.crypto.dto.CryptoRateDto;
import com.crypto.dto.ExchangeRateDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 虛擬貨幣物件轉換
 */
@Mapper
public interface CryptoConvertor {

    CryptoConvertor INSTANCE = Mappers.getMapper( CryptoConvertor.class );

    @Mapping(source = "chartName", target = "cryptoName")
    @Mapping(source="vo.time.updatedISO", target = "updatedTime",  qualifiedByName="toLocalTime")
    @Mapping(source = "vo.bpi", target = "exchangeRates", qualifiedByName = "mapToList")
    CryptoRateDto toDto(BitcoinVo vo);

    @Named("toLocalTime")
    default String map(String time) {
        return OffsetDateTime.parse(time).atZoneSameInstant(ZoneId.of("Asia/Taipei"))
                .format(java.time.format.DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
    }

    @Named("mapToList")
    default List<ExchangeRateDto> map(Map<String, BitcoinVo.RateVo> rates) {
        return rates.values().stream().map(r -> INSTANCE.toDto(r)).collect(Collectors.toList());
    }

    @Mapping(source = "rate", target = "rateFormat")
    @Mapping(source = "rateFloat", target = "rate")
    ExchangeRateDto toDto(BitcoinVo.RateVo rateVo);

}
