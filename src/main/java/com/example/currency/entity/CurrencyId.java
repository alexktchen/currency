package com.example.currency.entity;

import java.io.Serializable;

public class CurrencyId implements Serializable {
    private Integer id;

    private String countryCode;

    public CurrencyId(Integer id, String countryCode) {
        this.id = id;
        this.countryCode = countryCode;
    }

}
