package com.fenonq.oriltask.controller;

import com.fenonq.oriltask.api.CryptocurrencyApi;
import com.fenonq.oriltask.dto.CryptocurrencyDto;
import com.fenonq.oriltask.service.CSVConverter;
import com.fenonq.oriltask.service.CryptocurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class CryptocurrencyController implements CryptocurrencyApi {

    private final CryptocurrencyService cryptocurrencyService;
    private final CSVConverter converter;

    public List<CryptocurrencyDto> loadData(String name,
                                            Integer recordsNumber,
                                            Integer timeout) throws InterruptedException, URISyntaxException {
        return cryptocurrencyService.loadDataToDatabase(name, recordsNumber, timeout);
    }

    public CryptocurrencyDto lowestPriceCryptocurrency(String name) {
        return cryptocurrencyService.lowestPriceCryptocurrency(name);
    }

    public CryptocurrencyDto highestPriceCryptocurrency(String name) {
        return cryptocurrencyService.highestPriceCryptocurrency(name);
    }

    public Page<CryptocurrencyDto> findAll(Pageable pageable) {
        Page<CryptocurrencyDto> outCryptocurrencyDtoList = cryptocurrencyService.findAll(pageable);
        return new PageImpl<>(outCryptocurrencyDtoList.stream().collect(Collectors.toList()),
                pageable, outCryptocurrencyDtoList.getSize());
    }

    public void csvReport() throws IOException {
        converter.convert();
    }

}
