package com.example.currency.entity;

import lombok.Data;

@Data
public class CurrencyEntity {
    private String name;
    private String code;
    private String symbol;
    private String rate;
    private String description;
    private double rate_float;
}
