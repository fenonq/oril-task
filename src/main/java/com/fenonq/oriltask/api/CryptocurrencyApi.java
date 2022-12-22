package com.fenonq.oriltask.api;

import com.fenonq.oriltask.dto.CryptocurrencyDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Set;

@Api(tags = "Cryptocurrency management api")
@RequestMapping("/api/v1/cryptocurrencies")
public interface CryptocurrencyApi {

    @ApiOperation("Load data from CEX.IO to the database")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", dataType = "string", paramType = "query",
                    value = "Name of Cryptocurrency.", defaultValue = "BTC"),
            @ApiImplicitParam(name = "recordsNumber", dataType = "integer", paramType = "query",
                    value = "Number of records to load.", defaultValue = "3"),
            @ApiImplicitParam(name = "timeout", dataType = "integer", paramType = "query",
                    value = "requests delay", defaultValue = "60000")
    })
    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    List<CryptocurrencyDto> loadData(@RequestParam String name,
                                     @RequestParam Integer recordsNumber,
                                     @RequestParam Integer timeout) throws InterruptedException, URISyntaxException;

    @ApiOperation("Get Cryptocurrency by name with min price")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", paramType = "query",
                    value = "Cryptocurrency name", defaultValue = "BTC")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/minprice")
    CryptocurrencyDto lowestPriceCryptocurrency(@RequestParam String name);

    @ApiOperation("Get Cryptocurrency by name with max price")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", paramType = "query",
                    value = "Cryptocurrency name", defaultValue = "BTC")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/maxprice")
    CryptocurrencyDto highestPriceCryptocurrency(@RequestParam String name);

    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "Results page you want to retrieve (0..N)", defaultValue = "0"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Number of records per page.", defaultValue = "5"),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Sorting criteria in the format: property(,asc|desc). " +
                            "Default sort order is ascending. " +
                            "Multiple sort criteria are supported.")
    })
    @ApiOperation("Get all pageable Cryptocurrencies")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    Page<CryptocurrencyDto> findAll(@ApiIgnore("Ignored because swagger ui shows the wrong params")
                                            Pageable pageable);

    @ApiOperation("Generate a CSV report")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/csv")
    void csvReport() throws IOException;

}
