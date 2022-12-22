package com.fenonq.oriltask.service.impl;

import com.fenonq.oriltask.dto.CryptocurrencyDto;
import com.fenonq.oriltask.exception.CryptocurrencyNotFoundException;
import com.fenonq.oriltask.model.Cryptocurrency;
import com.fenonq.oriltask.repository.CryptocurrencyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static com.fenonq.oriltask.util.TestData.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CryptocurrencyServiceImplTest {

    @InjectMocks
    private CryptocurrencyServiceImpl cryptocurrencyService;

    @Mock
    private CryptocurrencyRepository cryptocurrencyRepository;

    @Mock
    private RestTemplate restTemplate;

    @Test
    void loadDataToDatabase() throws URISyntaxException, InterruptedException {
        Cryptocurrency cryptocurrency = createCryptocurrency();
        CryptocurrencyDto cryptocurrencyDto = createCryptocurrencyDto();

        when(restTemplate.getForObject(new URI(CEX_URL), CryptocurrencyDto.class))
                .thenReturn(cryptocurrencyDto);

        when(cryptocurrencyRepository.save(any())).thenReturn(cryptocurrency);

        List<CryptocurrencyDto> returnedCryptocurrencyDtoSet =
                cryptocurrencyService.loadDataToDatabase(NAME, RECORDS_NUMBER, TIMEOUT);

        assertThat(returnedCryptocurrencyDtoSet, hasSize(RECORDS_NUMBER));
    }

    @Test
    void loadDataToDatabaseCryptocurrencyNotFound() {
        assertThrows(CryptocurrencyNotFoundException.class,
                () -> cryptocurrencyService.loadDataToDatabase(WRONG_NAME, RECORDS_NUMBER, TIMEOUT));
    }

    @Test
    void lowestPriceCryptocurrency() {
        Cryptocurrency cryptocurrency = createCryptocurrency();

        when(cryptocurrencyRepository.findFirstByCurr1OrderByLpriceAsc(anyString()))
                .thenReturn(cryptocurrency);

        CryptocurrencyDto returnedCryptocurrency = cryptocurrencyService.lowestPriceCryptocurrency(anyString());
        verify(cryptocurrencyRepository).findFirstByCurr1OrderByLpriceAsc(anyString());
        assertThat(returnedCryptocurrency, allOf(
                hasProperty("curr1", equalTo(cryptocurrency.getCurr1())),
                hasProperty("curr2", equalTo(cryptocurrency.getCurr2()))
        ));
    }

    @Test
    void highestPriceCryptocurrency() {
        Cryptocurrency cryptocurrency = createCryptocurrency();

        when(cryptocurrencyRepository.findFirstByCurr1OrderByLpriceDesc(anyString()))
                .thenReturn(cryptocurrency);

        CryptocurrencyDto returnedCryptocurrency = cryptocurrencyService.highestPriceCryptocurrency(anyString());
        verify(cryptocurrencyRepository).findFirstByCurr1OrderByLpriceDesc(anyString());
        assertThat(returnedCryptocurrency, allOf(
                hasProperty("curr1", equalTo(cryptocurrency.getCurr1())),
                hasProperty("curr2", equalTo(cryptocurrency.getCurr2()))
        ));
    }

    @Test
    void findAll() {
        List<Cryptocurrency> cryptocurrencies = List.of(
                createCryptocurrency(),
                createCryptocurrency(),
                createCryptocurrency(),
                createCryptocurrency()
        );
        int size = 2;
        Pageable pageable = PageRequest.of(0, size);

        when(cryptocurrencyRepository.findAll(pageable))
                .thenReturn(new PageImpl<>(cryptocurrencies, pageable, cryptocurrencies.size()));

        Page<CryptocurrencyDto> returnedCryptocurrencies = cryptocurrencyService.findAll(pageable);

        verify(cryptocurrencyRepository).findAll(pageable);
        assertThat(returnedCryptocurrencies.getPageable(), is(pageable));
        assertThat(returnedCryptocurrencies.getSize(), is(size));
    }

}
