package com.example.currency.dao;

import com.example.currency.entity.Currency;
import org.springframework.data.repository.CrudRepository;

public interface CurrencyDao extends CrudRepository<Currency, String> {
}
