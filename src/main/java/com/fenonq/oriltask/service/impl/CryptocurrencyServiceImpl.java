package com.fenonq.oriltask.service.impl;

import com.fenonq.oriltask.dto.CryptocurrencyDto;
import com.fenonq.oriltask.exception.CryptocurrencyNotFoundException;
import com.fenonq.oriltask.mapper.CryptocurrencyMapper;
import com.fenonq.oriltask.model.Cryptocurrency;
import com.fenonq.oriltask.model.enums.CryptocurrencyPair;
import com.fenonq.oriltask.repository.CryptocurrencyRepository;
import com.fenonq.oriltask.service.CryptocurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.fenonq.oriltask.util.Constants.URL;

@Service
@RequiredArgsConstructor
public class CryptocurrencyServiceImpl implements CryptocurrencyService {

    private final CryptocurrencyRepository cryptocurrencyRepository;
    private final RestTemplate restTemplate;

    @Override
    public List<CryptocurrencyDto> loadDataToDatabase(String name,
                                                      Integer recordsNumber,
                                                      Integer timeout) throws InterruptedException, URISyntaxException {
        CryptocurrencyPair pair = CryptocurrencyPair.getPair(name).orElseThrow(CryptocurrencyNotFoundException::new);
        List<CryptocurrencyDto> cryptocurrencyDtoSet = new ArrayList<>();

        for (int i = 0; i < recordsNumber; i++) {
            CryptocurrencyDto cryptocurrencyDto = restTemplate
                    .getForObject(new URI(URL + pair), CryptocurrencyDto.class);
            Objects.requireNonNull(cryptocurrencyDto).setDateTime(LocalDateTime.now());
            cryptocurrencyDtoSet.add(cryptocurrencyDto);
            cryptocurrencyRepository
                    .save(CryptocurrencyMapper.INSTANCE.mapCryptocurrencyDtoToCryptocurrency(cryptocurrencyDto));
            Thread.sleep(timeout);
        }

        return cryptocurrencyDtoSet;
    }

    @Override
    public CryptocurrencyDto lowestPriceCryptocurrency(String name) {
        CryptocurrencyPair.getPair(name).orElseThrow(CryptocurrencyNotFoundException::new);
        Cryptocurrency lowestPriceCryptocurrency =
                cryptocurrencyRepository.findFirstByCurr1OrderByLpriceAsc(name.toUpperCase(Locale.ROOT));
        return CryptocurrencyMapper.INSTANCE.mapCryptocurrencyToCryptocurrencyDto(lowestPriceCryptocurrency);
    }

    @Override
    public CryptocurrencyDto highestPriceCryptocurrency(String name) {
        CryptocurrencyPair.getPair(name).orElseThrow(CryptocurrencyNotFoundException::new);
        Cryptocurrency highestPriceCryptocurrency =
                cryptocurrencyRepository.findFirstByCurr1OrderByLpriceDesc(name.toUpperCase(Locale.ROOT));
        return CryptocurrencyMapper.INSTANCE.mapCryptocurrencyToCryptocurrencyDto(highestPriceCryptocurrency);
    }

    @Override
    public Page<CryptocurrencyDto> findAll(Pageable pageable) {
        Page<Cryptocurrency> pagedResult = cryptocurrencyRepository.findAll(pageable);
        return new PageImpl<>(pagedResult.getContent().stream()
                .map(CryptocurrencyMapper.INSTANCE::mapCryptocurrencyToCryptocurrencyDto)
                .collect(Collectors.toList()), pageable, pagedResult.getSize());
    }

}
