package com.fenonq.oriltask.service;

import com.fenonq.oriltask.dto.CryptocurrencyDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.net.URISyntaxException;
import java.util.List;

public interface CryptocurrencyService {

    List<CryptocurrencyDto> loadDataToDatabase(String name,
                                               Integer recordsNumber,
                                               Integer timeout) throws InterruptedException, URISyntaxException;

    CryptocurrencyDto lowestPriceCryptocurrency(String name);

    CryptocurrencyDto highestPriceCryptocurrency(String name);

    Page<CryptocurrencyDto> findAll(Pageable pageable);

}
