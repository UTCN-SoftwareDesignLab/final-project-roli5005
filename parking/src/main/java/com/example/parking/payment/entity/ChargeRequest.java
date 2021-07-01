package com.example.parking.payment.entity;

import lombok.Data;

@Data
public class ChargeRequest {

    private long amount;
    private String currency;
}
