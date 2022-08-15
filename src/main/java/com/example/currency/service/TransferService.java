package com.example.currency.service;

import com.example.currency.dto.CoinDeskTransferDto;
import com.example.currency.dto.HttpResponseEntity;
import com.example.currency.entity.CoinDeskEntity;
import com.example.currency.entity.Currency;
import com.example.currency.entity.CurrencyEntity;
import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Map;

@Service
public class TransferService {

    private static final String coinDeskUrl = "https://api.coindesk.com/v1/bpi/currentprice.json";

    @Resource
    private CurrencyService currencyService;

    private CoinDeskEntity getCoinDesk() throws IOException {
        CloseableHttpClient client = HttpClientBuilder.create().build();
        org.apache.http.client.methods.CloseableHttpResponse res = client.execute(new HttpGet(coinDeskUrl));
        res.getStatusLine().getStatusCode();
        HttpEntity httpEntity = res.getEntity();

        if (httpEntity == null) {
            return null;
        }

        String result = EntityUtils.toString(httpEntity, "utf-8");
        Gson gson = new Gson();
        return gson.fromJson(result, CoinDeskEntity.class);
    }

    public HttpResponseEntity getCoinDeskItems() throws IOException {
        CoinDeskEntity coinDeskDto = getCoinDesk();
        if (coinDeskDto == null) {
            return HttpResponseEntity.fail("");
        }
        return HttpResponseEntity.ok(coinDeskDto);
    }


    public HttpResponseEntity getCoinDeskTransfer() throws IOException {

        CoinDeskEntity coinDeskDto = getCoinDesk();
        if (coinDeskDto == null) {
            return HttpResponseEntity.fail("");
        }

        Map<String, String> times = coinDeskDto.getTime();
        String time = times.get("updatedISO");
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .append(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            .optionalStart()
            .appendPattern(".SSS")
            .optionalEnd()
            .optionalStart()
            .appendZoneOrOffsetId()
            .optionalEnd()
            .optionalStart()
            .appendOffset("+HH:MM", "00:00")
            .optionalEnd()
            .toFormatter();

        LocalDateTime dateTime = LocalDateTime.parse(time, formatter);
        String date = dateTime.format(DateTimeFormatter.ofPattern("yyyy/MM/dd 00:00:00"));


        Iterable<Currency> currency = currencyService.getCurrency();
        for (Currency c : currency) {
            CurrencyEntity currencyEntity = coinDeskDto.getBpi().get(c.getCountryCode());
            if (currencyEntity != null) {
                currencyEntity.setName(c.getCountryName());

            }
        }

        CoinDeskTransferDto coinDeskTransferDto = new CoinDeskTransferDto();
        coinDeskTransferDto.setUpdateTime(date);
        coinDeskTransferDto.setCurrency(coinDeskDto.getBpi());

        return HttpResponseEntity.ok(coinDeskTransferDto);
    }

}
