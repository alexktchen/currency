package com.example.currency.dto;

import com.example.currency.entity.CurrencyEntity;
import lombok.Data;

import java.util.Map;

@Data
public class CoinDeskTransferDto {
    private String updateTime;
    private Map<String, CurrencyEntity> currency;
}
