package com.fenonq.oriltask.controller;

import com.fenonq.oriltask.dto.CryptocurrencyDto;
import com.fenonq.oriltask.service.CryptocurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cryptocurrencies")
public class CryptocurrencyController {

    private final CryptocurrencyService cryptocurrencyService;

    @PostMapping()
    public Set<CryptocurrencyDto> add(@RequestParam String name,
                                      @RequestParam Integer recordsNumber,
                                      @RequestParam Integer timeout) throws InterruptedException, URISyntaxException {
        return cryptocurrencyService.loadDataToDatabase(name, recordsNumber, timeout);
    }

    @GetMapping("/minprice")
    public CryptocurrencyDto lowestPriceCryptocurrency(@RequestParam String name) {
        return cryptocurrencyService.lowestPriceCryptocurrency(name);
    }

    @GetMapping("/maxprice")
    public CryptocurrencyDto highestPriceCryptocurrency(@RequestParam String name) {
        return cryptocurrencyService.highestPriceCryptocurrency(name);
    }

    @GetMapping()
    public Page<CryptocurrencyDto> findAll(Pageable pageable) {
        Page<CryptocurrencyDto> outCryptocurrencyDtoList = cryptocurrencyService.findAll(pageable);
        return new PageImpl<>(outCryptocurrencyDtoList.stream().collect(Collectors.toList()),
                pageable, outCryptocurrencyDtoList.getSize());
    }

}
