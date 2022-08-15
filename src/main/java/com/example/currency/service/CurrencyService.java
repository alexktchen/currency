package com.example.currency.service;

import com.example.currency.dao.CurrencyDao;
import com.example.currency.entity.Currency;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class CurrencyService {
    @Autowired
    CurrencyDao currencyDao;

    public Iterable<Currency> getCurrency() {
        return currencyDao.findAll();
    }

    @Transactional
    public Iterable<Currency> saveCurrencies(List<Currency> currency) {
        return currencyDao.saveAll(currency);
    }

    @Transactional
    public Currency saveCurrency(Currency currency) {
        if (!currencyDao.existsById(currency.getCountryCode())) {
            return null;
        }
        return currencyDao.save(currency);
    }

    @Transactional
    public boolean deleteCurrency(String code) {
        if (!currencyDao.existsById(code)) {
            return false;
        }
        currencyDao.deleteById(code);
        return true;
    }

}