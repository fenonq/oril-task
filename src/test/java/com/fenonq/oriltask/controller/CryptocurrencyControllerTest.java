package com.fenonq.oriltask.controller;

import com.fenonq.oriltask.dto.CryptocurrencyDto;
import com.fenonq.oriltask.service.CSVConverter;
import com.fenonq.oriltask.service.CryptocurrencyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.fenonq.oriltask.util.TestData.CRYPTOCURRENCY_URL;
import static com.fenonq.oriltask.util.TestData.createCryptocurrencyDto;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CryptocurrencyController.class)
class CryptocurrencyControllerTest {

    @MockBean
    private CryptocurrencyService cryptocurrencyService;

    @MockBean
    private CSVConverter csvConverter;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void loadData() throws Exception {
        List<CryptocurrencyDto> cryptocurrencyDtos = List.of(
                createCryptocurrencyDto(),
                createCryptocurrencyDto());

        when(cryptocurrencyService.loadDataToDatabase(anyString(), anyInt(), anyInt()))
                .thenReturn(cryptocurrencyDtos);

        mockMvc.perform(post(CRYPTOCURRENCY_URL)
                        .queryParam("name", "BTC")
                        .queryParam("recordsNumber", "3")
                        .queryParam("timeout", "100"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].curr1").value(cryptocurrencyDtos.get(0).getCurr1()));
        verify(cryptocurrencyService).loadDataToDatabase(anyString(), anyInt(), anyInt());
    }

    @Test
    void lowestPriceCryptocurrency() throws Exception {
        CryptocurrencyDto cryptocurrencyDto = createCryptocurrencyDto();

        when(cryptocurrencyService.lowestPriceCryptocurrency(anyString())).thenReturn(cryptocurrencyDto);

        mockMvc.perform(get(CRYPTOCURRENCY_URL + "/minprice?name=BTC"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.curr1").value(cryptocurrencyDto.getCurr1()));
        verify(cryptocurrencyService).lowestPriceCryptocurrency(anyString());
    }

    @Test
    void highestPriceCryptocurrency() throws Exception {
        CryptocurrencyDto cryptocurrencyDto = createCryptocurrencyDto();

        when(cryptocurrencyService.highestPriceCryptocurrency(anyString())).thenReturn(cryptocurrencyDto);

        mockMvc.perform(get(CRYPTOCURRENCY_URL + "/maxprice")
                        .queryParam("name", "BTC"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.curr1").value(cryptocurrencyDto.getCurr1()));
        verify(cryptocurrencyService).highestPriceCryptocurrency(anyString());
    }

    @Test
    void findAll() throws Exception {
        int page = 0;
        int size = 2;
        List<CryptocurrencyDto> cryptocurrencyDtos = List.of(
                createCryptocurrencyDto(),
                createCryptocurrencyDto());
        Pageable pageable = PageRequest.of(page, size);

        Page<CryptocurrencyDto> cryptocurrencyDtoPage =
                new PageImpl<>(cryptocurrencyDtos, pageable, cryptocurrencyDtos.size());

        when(cryptocurrencyService.findAll(any(Pageable.class))).thenReturn(cryptocurrencyDtoPage);

        mockMvc.perform(get(CRYPTOCURRENCY_URL)
                        .queryParam("page", String.valueOf(page))
                        .queryParam("size", String.valueOf(size)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.pageable.pageNumber").value(page))
                .andExpect(jsonPath("$.pageable.pageSize").value(size))
                .andExpect(jsonPath("$.content[0].curr1").value(cryptocurrencyDtos.get(0).getCurr1()));
        verify(cryptocurrencyService).findAll(any(Pageable.class));
    }

    @Test
    void csvReport() throws Exception {
        doNothing().when(csvConverter).convert();

        mockMvc.perform(get(CRYPTOCURRENCY_URL + "/csv"))
                .andExpect(status().isOk());

        verify(csvConverter).convert();
    }

}
