package com.api.demo.controller;
import com.api.demo.dto.aluno.AlunoCreateDTO;
import com.api.demo.dto.aluno.AlunoResponseDTO;
import com.api.demo.dto.aluno.AlunoUpdateDTO;
import com.api.demo.services.AlunoServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.api.demo.model.Aluno;
import java.util.List;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    private final AlunoServices alunoServices;

    public AlunoController(AlunoServices alunoServices){
        this.alunoServices = alunoServices;
    }

    @GetMapping("/")
    public ResponseEntity<List<AlunoResponseDTO>> findAll(){
        List<AlunoResponseDTO> alunoResponseDTOList = alunoServices.findAll();
        System.out.println(alunoResponseDTOList + "- print do controller");
        return ResponseEntity.ok(alunoResponseDTOList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlunoResponseDTO> findById(
            @PathVariable Long id
    ){
        AlunoResponseDTO alunoResponseDTO = alunoServices.findById(id);

        return ResponseEntity.ok(alunoResponseDTO);
    }

    @PostMapping("/")
    public ResponseEntity<AlunoResponseDTO> save(
            @RequestBody AlunoCreateDTO dto
    ){
        AlunoResponseDTO alunoResponseDTO = alunoServices.save(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(alunoResponseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlunoResponseDTO> update(
            @PathVariable Long id,
            @RequestBody AlunoUpdateDTO dto
            ){
        AlunoResponseDTO alunoResponseDTO =  alunoServices.update(id, dto);
        return ResponseEntity.ok(alunoResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ){
        alunoServices.delete(id);
        return ResponseEntity.noContent().build();
    }
}

