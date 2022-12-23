package com.fenonq.oriltask.service.impl;

import com.fenonq.oriltask.model.Cryptocurrency;
import com.fenonq.oriltask.repository.CryptocurrencyRepository;
import com.fenonq.oriltask.util.Constants;
import com.fenonq.oriltask.util.TestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReportServiceImplTest {

    @InjectMocks
    private ReportServiceImpl reportService;

    @Mock
    private CryptocurrencyRepository cryptocurrencyRepository;

    @Test
    void convert() throws IOException {
        Cryptocurrency cryptocurrency = TestData.createCryptocurrency();

        when(cryptocurrencyRepository.findFirstByCurr1OrderByLpriceAsc(anyString()))
                .thenReturn(cryptocurrency);

        when(cryptocurrencyRepository.findFirstByCurr1OrderByLpriceDesc(anyString()))
                .thenReturn(cryptocurrency);

        reportService.CSVReport();

        File file = new File(Constants.CSV_FILE_NAME);

        assertTrue(file.exists());
        verify(cryptocurrencyRepository, times(TestData.RECORDS_NUMBER))
                .findFirstByCurr1OrderByLpriceAsc(anyString());
        verify(cryptocurrencyRepository, times(TestData.RECORDS_NUMBER))
                .findFirstByCurr1OrderByLpriceDesc(anyString());
    }

}
