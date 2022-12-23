package com.fenonq.oriltask.service.impl;

import com.fenonq.oriltask.model.enums.CryptocurrencyPair;
import com.fenonq.oriltask.repository.CryptocurrencyRepository;
import com.fenonq.oriltask.service.ReportService;
import com.fenonq.oriltask.util.Constants;
import com.opencsv.CSVWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final CryptocurrencyRepository cryptocurrencyRepository;

    public void CSVReport() throws IOException {
        log.info("creating CSV report");
        FileWriter outputFile = new FileWriter(Constants.CSV_FILE_NAME);
        CSVWriter writer = new CSVWriter(outputFile,
                CSVWriter.DEFAULT_SEPARATOR,
                CSVWriter.NO_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END);

        writer.writeNext(Constants.CSV_HEADERS);

        String[] data;
        for (CryptocurrencyPair cryptocurrencyPair : CryptocurrencyPair.values()) {
            String cryptocurrencyName = cryptocurrencyPair.toString().substring(0, 3);

            data = new String[]{
                    cryptocurrencyName,
                    cryptocurrencyRepository.findFirstByCurr1OrderByLpriceAsc(cryptocurrencyName)
                            .getLprice().toString(),
                    cryptocurrencyRepository.findFirstByCurr1OrderByLpriceDesc(cryptocurrencyName)
                            .getLprice().toString()
            };
            writer.writeNext(data);
        }
        writer.close();
    }

}
