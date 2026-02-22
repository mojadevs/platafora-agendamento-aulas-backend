package com.api.demo.controller;

import com.api.demo.dto.instrutor.InstrutorCreateDTO;
import com.api.demo.services.InstrutorServices;
import com.api.demo.services.PagamentoServices;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stripe.exception.StripeException;
import com.stripe.model.Account;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.net.Webhook;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/webhook/stripe")
public class WebHookController {

    @Value("${stripe.webhook.secret}")
    private String endpointSecret;

    private final PagamentoServices pagamentoServices;
    private final InstrutorServices instrutorServices;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public WebHookController(PagamentoServices pagamentoServices, InstrutorServices instrutorServices) {
        this.pagamentoServices = pagamentoServices;
        this.instrutorServices = instrutorServices;
    }

    @PostMapping("/webhook")
    public ResponseEntity<String> handleWebhook(
            HttpServletRequest request,
            @RequestHeader("Stripe-Signature") String sigHeader
    ) throws IOException {

        String payload = new String(request.getInputStream().readAllBytes());
        Event event;

        try {
            event = Webhook.constructEvent(payload, sigHeader, endpointSecret);
        } catch (Exception e) {
            System.out.println("Webhook inválido: " + e.getMessage());
            return ResponseEntity.status(403).body("Invalid signature");
        }

        System.out.println("Evento recebido: " + event.getType());

        switch (event.getType()) {
            case "payment_intent.succeeded":
                PaymentIntent intent = (PaymentIntent) event.getDataObjectDeserializer()
                        .getObject()
                        .orElse(null);
                if (intent != null) {
                    pagamentoServices.updateStatus(intent.getId(), "CONFIRMADO");
                    System.out.println("PaymentIntent confirmado: " + intent.getId());
                }
                break;

            case "account.updated":
                // Aqui você pode atualizar dados do instrutor se quiser
                System.out.println("Account updated: " + event.getAccount());
                break;

            case "capability.updated":
                try {
                    // Pega o JSON bruto do evento
                    JsonNode jsonNode = objectMapper.readTree(event.getData().getObject().toJson());
                    String accountId = jsonNode.get("account").asText();
                    System.out.println("⚡ Capability updated para account: " + accountId);

                    // Busca a conta completa
                    Account accountCap = Account.retrieve(accountId);

                    //accountCap.getChargesEnabled() && accountCap.getDetailsSubmitted()
                    if (true) {
                        InstrutorCreateDTO dto = new InstrutorCreateDTO();
                        dto.setEmail(accountCap.getEmail());
                        dto.setAccountId(accountCap.getId());
                        dto.setNome(accountCap.getMetadata().get("nome"));
                        dto.setSenha(accountCap.getMetadata().get("senha"));
                        dto.setTelefone(accountCap.getMetadata().get("telefone"));
                        dto.setPrecoHora(Double.parseDouble(accountCap.getMetadata().get("precoHora")));
                        dto.setAtivo(Boolean.parseBoolean(accountCap.getMetadata().get("ativo")));

                        instrutorServices.save(dto);
                        System.out.println("Instrutor criado: " + dto.getNome());
                    }

                } catch (StripeException e) {
                    System.out.println("⚠️ Erro ao buscar Account no Stripe: " + e.getMessage());
                }
                break;

            default:
                System.out.println("ℹ️ Evento ignorado: " + event.getType());
                break;
        }

        return ResponseEntity.ok("");
    }
}