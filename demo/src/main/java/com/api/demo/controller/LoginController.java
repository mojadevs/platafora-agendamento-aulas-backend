package com.api.demo.controller;

import com.api.demo.dto.login.LoginDTO;
import com.api.demo.dto.login.LoginResponseDTO;
import com.api.demo.services.LoginServices;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class LoginController {

    private final LoginServices loginServices;

    public LoginController(LoginServices loginServices){
        this.loginServices = loginServices;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @RequestBody LoginDTO dto
    ){
        LoginResponseDTO loginResponseDTO = loginServices.login(dto);
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + loginResponseDTO.getToken())
                .body(loginResponseDTO);
    }
}
