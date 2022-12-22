package com.fenonq.oriltask.model.enums;

import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;

public enum CryptocurrencyPair {
    BTC_USD("BTC/USD"),
    ETH_USD("ETH/USD"),
    XRP_USD("XRP/USD");

    private final String name;

    CryptocurrencyPair(String name) {
        this.name = name;
    }

    public static Optional<CryptocurrencyPair> getPair(String name) {
        return Arrays.stream(CryptocurrencyPair.values())
                .filter(el -> el.toString().startsWith(name.toUpperCase(Locale.ROOT)))
                .findFirst();
    }

    @Override
    public String toString() {
        return name;
    }
}
