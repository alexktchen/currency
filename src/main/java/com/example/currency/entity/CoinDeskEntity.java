package com.example.currency.entity;

import lombok.Data;

import java.util.Map;

@Data
public class CoinDeskEntity {
    private Map<String, String> time;
    private String disclaimer;
    private String chartName;
    private Map<String, CurrencyEntity> bpi;
}
