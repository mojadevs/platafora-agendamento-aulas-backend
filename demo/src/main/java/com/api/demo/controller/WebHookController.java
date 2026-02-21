package com.api.demo.controller;

import com.api.demo.dto.instrutor.InstrutorCreateDTO;
import com.api.demo.services.InstrutorServices;
import com.api.demo.services.PagamentoServices;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Account;
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
    private final InstrutorServices instrutorServices;

    public WebHookController(PagamentoServices pagamentoServices, InstrutorServices instrutorServices){
        this.pagamentoServices = pagamentoServices;
        this.instrutorServices = instrutorServices;
    }

    @PostMapping("/webhook")
    public ResponseEntity<String> handleWebhook(
            HttpServletRequest request,
            @RequestHeader("Stripe-Signature") String sigHeader) throws IOException {

        String payload = new String(request.getInputStream().readAllBytes());

        Event event;

        try {
            event = Webhook.constructEvent(payload, sigHeader, endpointSecret);
        } catch (SignatureVerificationException e) {
            return ResponseEntity.status(400).body("Invalid signature");
        }

        if ("payment_intent.succeeded".equals(event.getType())) {

            PaymentIntent intent = (PaymentIntent) event
                    .getDataObjectDeserializer()
                    .getObject()
                    .orElse(null);

            if (intent != null) {
                pagamentoServices.updateStatus(intent.getId(), "CONFIRMADO");
            }

        } else if ("account.updated".equals(event.getType())) {

            Account account = (Account) event
                    .getDataObjectDeserializer()
                    .getObject()
                    .orElse(null);

            if (account != null &&
                    account.getChargesEnabled() &&
                    account.getDetailsSubmitted()) {

                InstrutorCreateDTO dto = new InstrutorCreateDTO();

                dto.setEmail(account.getEmail());
                dto.setAccountId(account.getId());
                dto.setNome(account.getMetadata().get("nome"));
                dto.setSenha(account.getMetadata().get("senha"));
                dto.setTelefone(account.getMetadata().get("telefone"));
                dto.setPrecoHora(Double.parseDouble(account.getMetadata().get("precoHora")));
                dto.setAtivo(Boolean.getBoolean(account.getMetadata().get("ativo")));

                instrutorServices.save(dto);
            }
        }

        return ResponseEntity.ok("");
    }
}
