package com.example.currency.controller;

import com.example.currency.dto.HttpResponseEntity;
import com.example.currency.service.TransferService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;

@RestController
@RequestMapping("/coindesk")
public class CoindeskController {


    @Resource
    TransferService transferService;

    @GetMapping
    public HttpResponseEntity getCoinDesk() throws IOException {
        return transferService.getCoinDeskItems();
    }

    @GetMapping("/transfer")
    public HttpResponseEntity getCoinDeskTransfer() throws IOException {
        return transferService.getCoinDeskTransfer();
    }
}
