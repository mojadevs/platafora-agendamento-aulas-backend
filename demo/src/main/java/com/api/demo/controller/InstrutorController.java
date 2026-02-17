package com.api.demo.controller;
import com.api.demo.dto.instrutor.InstrutorCreateDTO;
import com.api.demo.dto.instrutor.InstrutorResponseDTO;
import com.api.demo.dto.instrutor.InstrutorUpdateDTO;
import com.api.demo.services.AlunoServices;
import com.api.demo.services.InstrutorServices;
import com.stripe.exception.StripeException;
import com.stripe.model.Account;
import com.stripe.model.AccountLink;
import com.stripe.param.AccountCreateParams;
import com.stripe.param.AccountLinkCreateParams;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.api.demo.model.Instrutor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/instrutores")
public class InstrutorController {

    private final InstrutorServices instrutorServices;

    public InstrutorController(InstrutorServices instrutorServices){
        this.instrutorServices = instrutorServices;
    }

    @GetMapping("/")
    public ResponseEntity<List<InstrutorResponseDTO>> findAll(){

        List<InstrutorResponseDTO> instrutorResponseDTOList =  instrutorServices.findAll();
        return ResponseEntity.ok(instrutorResponseDTOList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InstrutorResponseDTO> findById(
            @PathVariable Long id
    ){
        InstrutorResponseDTO instrutorResponseDTO = instrutorServices.findById(id);
        return ResponseEntity.ok(instrutorResponseDTO);
    }

    @PostMapping("/")
    public ResponseEntity<Map<String, Object>> save(
            @RequestBody InstrutorCreateDTO dto
    ) {
        try {

            AccountCreateParams accountParams = AccountCreateParams.builder()
                    .setType(AccountCreateParams.Type.EXPRESS)
                    .setCountry("BR")
                    .setEmail(dto.getEmail())
                    .putMetadata("nome", dto.getNome())
                    .putMetadata("senha", dto.getSenha())
                    .putMetadata("telefone", dto.getTelefone())
                    .putMetadata("precoHora", String.valueOf(dto.getPrecoHora()))
                    .putMetadata("ativo", String.valueOf(dto.getAtivo()))
                    .build();

            Account account = Account.create(accountParams);

            AccountLinkCreateParams linkParams =
                    AccountLinkCreateParams.builder()
                            .setAccount(account.getId())
                            .setRefreshUrl("http://localhost:3000/refresh")
                            .setReturnUrl("http://localhost:3000/success")
                            .setType(AccountLinkCreateParams.Type.ACCOUNT_ONBOARDING)
                            .build();

            AccountLink accountLink = AccountLink.create(linkParams);

            Map<String, Object> result = new HashMap<>();
            result.put("accountId", account.getId());
            result.put("onboardingUrl", accountLink.getUrl());

            return ResponseEntity.status(HttpStatus.CREATED).body(result);

        } catch (StripeException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", e.getMessage()));
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<InstrutorResponseDTO> update(
            @PathVariable Long id,
            @RequestBody InstrutorUpdateDTO dto
    ){
        InstrutorResponseDTO instrutorResponseDTO = instrutorServices.update(id, dto);
        return ResponseEntity.ok(instrutorResponseDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable Long id
    ){
        instrutorServices.delete(id);
    }
}

