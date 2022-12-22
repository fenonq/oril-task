package com.fenonq.oriltask.util;

import com.fenonq.oriltask.dto.CryptocurrencyDto;
import com.fenonq.oriltask.model.Cryptocurrency;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestData {

    public static final String NAME = "BTC";
    public static final String WRONG_NAME = "BTS";
    public static final String CEX_URL = "https://cex.io/api/last_price/BTC/USD";
    public static final String CRYPTOCURRENCY_URL = "/api/v1/cryptocurrencies";
    public static final Integer RECORDS_NUMBER = 3;
    public static final Integer TIMEOUT = 100;


    public static Cryptocurrency createCryptocurrency() {
        return Cryptocurrency.builder()
                .curr1("BTC")
                .curr2("USD")
                .lprice(new BigDecimal(1234))
                .dateTime(LocalDateTime.now())
                .build();
    }

    public static CryptocurrencyDto createCryptocurrencyDto() {
        return CryptocurrencyDto.builder()
                .curr1("BTC")
                .curr2("USD")
                .lprice(new BigDecimal(1234))
                .dateTime(LocalDateTime.now())
                .build();
    }

}
