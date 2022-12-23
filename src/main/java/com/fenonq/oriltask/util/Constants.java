package com.fenonq.oriltask.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {
    public static final String URL = "https://cex.io/api/last_price/";
    public static final String CSV_FILE_NAME = "report.csv";
    public static final String[] CSV_HEADERS =
            new String[]{"Cryptocurrency Name", "Min Price", "Max Price"};
}
