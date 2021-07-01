package com.example.parking.payment;


import com.example.parking.payment.entity.ChargeRequest;
import com.example.parking.payment.entity.ChargeResponse;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StripeService {

    @Value("${stripe.keys.secret}")
    private String API_SECRET_KEY;

    public ChargeResponse createCharge(ChargeRequest chargeRequest) throws StripeException {
        Stripe.apiKey = API_SECRET_KEY;
        PaymentIntentCreateParams createParams = new PaymentIntentCreateParams.Builder()
                .setCurrency(chargeRequest.getCurrency())
                .setAmount(chargeRequest.getAmount())
                .setDescription("Parking payment")
                .addPaymentMethodType("card")
                .build();
        return ChargeResponse.builder().response(PaymentIntent.create(createParams).getClientSecret()).build();
    }
}
