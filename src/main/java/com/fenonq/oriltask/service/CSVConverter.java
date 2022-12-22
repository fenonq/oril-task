package com.fenonq.oriltask.service;

import com.fenonq.oriltask.model.enums.CryptocurrencyPair;
import com.fenonq.oriltask.service.CryptocurrencyService;
import com.opencsv.CSVWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class CSVConverter {

    private final CryptocurrencyService cryptocurrencyService;

    public void convert() throws IOException {
        FileWriter outputfile = new FileWriter("report.csv");
        CSVWriter writer = new CSVWriter(outputfile,
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
                    cryptocurrencyService.lowestPriceCryptocurrency(cryptocurrencyName).getLprice().toString(),
                    cryptocurrencyService.highestPriceCryptocurrency(cryptocurrencyName).getLprice().toString()
            };
            writer.writeNext(data);
        }

        writer.close();
    }
}
