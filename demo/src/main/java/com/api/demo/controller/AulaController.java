package com.api.demo.controller;
import com.api.demo.dto.aula.AulaCreateDTO;
import com.api.demo.dto.aula.AulaResponseDTO;
import com.api.demo.dto.aula.AulaUpdateDTO;
import com.api.demo.services.AlunoServices;
import com.api.demo.services.AulaServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.api.demo.model.Aula;
import java.util.List;

@RestController
@RequestMapping("/aulas")
public class AulaController {

    private final AulaServices aulaServices;

    public AulaController(AulaServices aulaServices){
        this.aulaServices = aulaServices;
    }

    @GetMapping("/")
    public ResponseEntity<List<AulaResponseDTO>> findAll(){
        List<AulaResponseDTO> aulaResponseDTOList = aulaServices.findAll();
        return ResponseEntity.ok(aulaResponseDTOList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AulaResponseDTO> findById(
            @PathVariable Long id
    ){
        AulaResponseDTO aulaResponseDTO = aulaServices.findById(id);
        return ResponseEntity.ok(aulaResponseDTO);
    }

    @PostMapping("/")
    public ResponseEntity<AulaResponseDTO> save(
            @RequestBody AulaCreateDTO aulaCreateDTO
            ){
        AulaResponseDTO aulaResponseDTO = aulaServices.save(aulaCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(aulaResponseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AulaResponseDTO> update(
            @PathVariable Long id,
            @RequestBody AulaUpdateDTO dto
            ){
        AulaResponseDTO aulaResponseDTO = aulaServices.update(id, dto);
        return ResponseEntity.ok(aulaResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ){
        aulaServices.delete(id);
        return ResponseEntity.noContent().build();
    }
}

