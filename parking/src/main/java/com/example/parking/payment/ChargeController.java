package com.example.parking.payment;

import com.example.parking.payment.entity.ChargeRequest;
import com.example.parking.payment.entity.ChargeResponse;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.example.parking.UrlMapping.PAYMENT;

@Controller
@RequestMapping(PAYMENT)
@RequiredArgsConstructor
public class ChargeController {

    @Value("${stripe.keys.public}")
    private String API_PUBLIC_KEY;

    private final StripeService stripeService;

    @PostMapping
    @ResponseBody
    public ChargeResponse createCharge(@RequestBody ChargeRequest chargeRequest) throws StripeException {

        return stripeService.createCharge(chargeRequest);
    }
}
