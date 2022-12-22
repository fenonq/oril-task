package com.fenonq.oriltask.repository;

import com.fenonq.oriltask.model.Cryptocurrency;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CryptocurrencyRepository extends MongoRepository<Cryptocurrency, String> {

    Cryptocurrency findFirstByCurr1OrderByLpriceAsc(String firstCurrency);

    Cryptocurrency findFirstByCurr1OrderByLpriceDesc(String firstCurrency);

}
