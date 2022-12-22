package com.fenonq.oriltask.service;

import com.fenonq.oriltask.model.enums.CryptocurrencyPair;
import com.fenonq.oriltask.repository.CryptocurrencyRepository;
import com.opencsv.CSVWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class CSVConverter {

    private final CryptocurrencyRepository cryptocurrencyRepository;

    public void convert() throws IOException {
        log.info("creating CSV report");
        FileWriter outputFile = new FileWriter("report.csv");
        CSVWriter writer = new CSVWriter(outputFile,
                CSVWriter.DEFAULT_SEPARATOR,
                CSVWriter.NO_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END);

        String[] headers = new String[]{"Cryptocurrency Name", "Min Price", "Max Price"};
        writer.writeNext(headers);

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
