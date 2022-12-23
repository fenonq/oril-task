package com.fenonq.oriltask.controller;

import com.fenonq.oriltask.api.CryptocurrencyApi;
import com.fenonq.oriltask.dto.CryptocurrencyDto;
import com.fenonq.oriltask.service.CryptocurrencyService;
import com.fenonq.oriltask.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CryptocurrencyController implements CryptocurrencyApi {

    private final CryptocurrencyService cryptocurrencyService;
    private final ReportService reportService;

    public List<CryptocurrencyDto> loadData(String name,
                                            Integer recordsNumber,
                                            Integer timeout) throws InterruptedException, URISyntaxException {
        log.info("loading {} records, Cryptocurrency: {}", recordsNumber, name);
        return cryptocurrencyService.loadDataToDatabase(name, recordsNumber, timeout);
    }

    public CryptocurrencyDto lowestPriceCryptocurrency(String name) {
        log.info("find {} with lowest price", name);
        return cryptocurrencyService.lowestPriceCryptocurrency(name);
    }

    public CryptocurrencyDto highestPriceCryptocurrency(String name) {
        log.info("find {} with highest price", name);
        return cryptocurrencyService.highestPriceCryptocurrency(name);
    }

    public Page<CryptocurrencyDto> findAll(Pageable pageable) {
        log.info("find all pageable Cryptocurrencies {}", pageable);
        Page<CryptocurrencyDto> outCryptocurrencyDtoList = cryptocurrencyService.findAll(pageable);
        return new PageImpl<>(outCryptocurrencyDtoList.stream().collect(Collectors.toList()),
                pageable, outCryptocurrencyDtoList.getSize());
    }

    public void csvReport() throws IOException {
        log.info("creating CSV report");
        reportService.CSVReport();
    }

}
