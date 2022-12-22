package com.fenonq.oriltask.mapper;

import com.fenonq.oriltask.dto.CryptocurrencyDto;
import com.fenonq.oriltask.model.Cryptocurrency;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CryptocurrencyMapper {

    CryptocurrencyMapper INSTANCE = Mappers.getMapper(CryptocurrencyMapper.class);

    Cryptocurrency mapCryptocurrencyDtoToCryptocurrency(CryptocurrencyDto cryptocurrencyDto);

    CryptocurrencyDto mapCryptocurrencyToCryptocurrencyDto(Cryptocurrency cryptocurrency);

}
