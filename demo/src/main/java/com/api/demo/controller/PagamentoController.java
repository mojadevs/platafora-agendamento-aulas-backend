package com.api.demo.controller;
import com.api.demo.dto.instrutor.InstrutorResponseDTO;
import com.api.demo.dto.pagamento.PagamentoCreateDTO;
import com.api.demo.dto.pagamento.PagamentoResponseDTO;
import com.api.demo.dto.pagamento.PagamentoUpdateDTO;
import com.api.demo.mapper.InstrutorMapper;
import com.api.demo.mapper.PagamentoMapper;
import com.api.demo.model.Instrutor;
import com.api.demo.services.AlunoServices;
import com.api.demo.services.InstrutorServices;
import com.api.demo.services.PagamentoServices;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.net.Webhook;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.query.Page;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.api.demo.model.Pagamento;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    private final PagamentoServices pagamentoServices;
    private final InstrutorServices instrutorServices;
    private final InstrutorMapper instrutorMapper;
    @Value("${stripe.webhook.secret}")
    private String endpointSecret;

    public PagamentoController(PagamentoServices pagamentoServices, InstrutorServices instrutorServices, InstrutorMapper instrutorMapper){
        this.pagamentoServices = pagamentoServices;
        this.instrutorServices = instrutorServices;
        this.instrutorMapper = instrutorMapper;
    }

    @GetMapping("/")
    public ResponseEntity<List<PagamentoResponseDTO>> findAll(){
        List<PagamentoResponseDTO> pagamentos = pagamentoServices.findAll();
        return ResponseEntity.ok(pagamentos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagamentoResponseDTO> findById(
            @PathVariable Long id
    ){
        PagamentoResponseDTO pagamentoResponseDTO = pagamentoServices.findById(id);
        return ResponseEntity.ok(pagamentoResponseDTO);
    }

    //O id é do instrutor, não do pagamento
    @PostMapping("/{id}")
    public ResponseEntity<PagamentoResponseDTO> save(
            @RequestBody PagamentoCreateDTO dto,
            @PathVariable Long id
    )throws Exception {
        try{
            PagamentoResponseDTO pagamentoResponseDTO = pagamentoServices.save(dto);
            InstrutorResponseDTO instrutorResponseDTO = instrutorServices.findById(id);
            String instructorAccountId = instrutorResponseDTO.getAccountId();

            if (!pagamentoServices.activeAccount(instructorAccountId)) {
                throw new RuntimeException("Conta ainda não está habilitada para receber pagamentos");
            }

            Double valorInstrutorDouble = pagamentoResponseDTO.getValorInstrutor();
            Double valorPlataformaDouble = pagamentoResponseDTO.getValorPlataforma();


            Integer valorInstrutor = (int) Math.round(valorInstrutorDouble * 100);
            Integer valorPlataforma = (int) Math.round(valorPlataformaDouble * 100);

            Map<String, Object> params = new HashMap<>();
            params.put("amount", valorInstrutor);
            params.put("currency", "brl");

            params.put("application_fee_amount", valorPlataforma);

            Map<String, Object> transferData = new HashMap<>();
            transferData.put("destination", instructorAccountId);

            params.put("transfer_data", transferData);
            PaymentIntent paymentIntent = PaymentIntent.create(params);
            String paymentIntentId = paymentIntent.getId();
            pagamentoResponseDTO.setPaymentIntentId(paymentIntentId);
            String clientSecret = paymentIntent.getClientSecret();
            pagamentoResponseDTO.setClientSecret(clientSecret);

            return ResponseEntity.status(HttpStatus.CREATED).body(pagamentoResponseDTO);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PagamentoResponseDTO> update(
            @PathVariable Long id,
            @RequestBody PagamentoUpdateDTO dto
            ){
        PagamentoResponseDTO pagamentoResponseDTO = pagamentoServices.update(id, dto);
        return ResponseEntity.ok(pagamentoResponseDTO);
    }




    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ){
        pagamentoServices.delete(id);
        return ResponseEntity.noContent().build();
    }
}