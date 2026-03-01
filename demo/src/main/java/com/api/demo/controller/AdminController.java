package com.api.demo.controller;

import com.api.demo.dto.admin.AdminCreateDTO;
import com.api.demo.dto.admin.AdminResponseDTO;
import com.api.demo.services.AdminServices;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/private")
public class AdminController {

    private final AdminServices adminServices;


    public AdminController(AdminServices adminServices){
        this.adminServices = adminServices;
    }

    @PostMapping("/")
    public ResponseEntity<AdminResponseDTO> save(
            @RequestBody AdminCreateDTO dto
    ){
        AdminResponseDTO adminResponseDTO = adminServices.save(dto);

        return ResponseEntity.ok(adminResponseDTO);
    }
}
