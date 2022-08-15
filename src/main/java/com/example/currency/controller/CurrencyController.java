package com.example.currency.controller;

import com.example.currency.dto.HttpResponseEntity;
import com.example.currency.entity.Currency;
import com.example.currency.service.CurrencyService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/currency")
public class CurrencyController {

    @Resource
    CurrencyService currencyService;

    @GetMapping
    public HttpResponseEntity getCurrencies() {
        return HttpResponseEntity.ok(currencyService.getCurrency());
    }

    @PostMapping
    public HttpResponseEntity saveCurrencies(@RequestBody List<Currency> currency) {
        currencyService.saveCurrencies(currency);
        return HttpResponseEntity.ok(currency);
    }

    @PutMapping
    public HttpResponseEntity updateCurrency(@RequestBody Currency currency) {
        Currency updated = currencyService.saveCurrency(currency);
        if (updated == null) {
            return HttpResponseEntity.fail("Item not existing");
        }
        return HttpResponseEntity.ok(updated);
    }

    @DeleteMapping
    public HttpResponseEntity deleteCurrency(@RequestParam("code") String code) {
        boolean success = currencyService.deleteCurrency(code);
        return success ? HttpResponseEntity.ok() : HttpResponseEntity.fail("item not existing");
    }
}
