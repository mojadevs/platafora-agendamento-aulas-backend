package com.api.demo.controller;

import com.api.demo.services.PagamentoServices;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.net.Webhook;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/webhook/stripe")
public class WebHookController {
    @Value("${stripe.webhook.secret}")
    private String endpointSecret;

    private final PagamentoServices pagamentoServices;

    public WebHookController(PagamentoServices pagamentoServices){
        this.pagamentoServices = pagamentoServices;
    }

    @PostMapping("/webhook")
    public ResponseEntity<String> handleWebhook(HttpServletRequest request, @RequestHeader("Stripe-Signature") String sigHeader) throws IOException {
        String payload = request.getReader()
                .lines()
                .reduce("", (accumulator, actual) -> accumulator + actual);

        Event event;

        try {
            event = Webhook.constructEvent(payload, sigHeader, endpointSecret);
        } catch (SignatureVerificationException e) {
            return ResponseEntity.status(400).body("Invalid signature");
        }

        // Evento confirmado como legítimo

        if ("payment_intent.succeeded".equals(event.getType())) {

            PaymentIntent intent = (PaymentIntent) event.getDataObjectDeserializer().getObject().get();
            String paymentIntentId = intent.getId();
            String status = "CONFIRMADO";

            pagamentoServices.updateStatus(paymentIntentId, status);
        }else if("account.updated".equals(event.getType())){

        }

        return ResponseEntity.ok("");
    }
}
