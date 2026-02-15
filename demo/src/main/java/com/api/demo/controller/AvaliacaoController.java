package com.api.demo.controller;
import com.api.demo.dto.avaliacao.AvaliacaoCreateDTO;
import com.api.demo.dto.avaliacao.AvaliacaoResponseDTO;
import com.api.demo.dto.avaliacao.AvaliacaoUpdateDTO;
import com.api.demo.model.Aula;
import com.api.demo.services.AlunoServices;
import com.api.demo.services.AvaliacaoServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.api.demo.model.Avaliacao;
import java.util.List;

@RestController
@RequestMapping("/avaliacoes")
public class AvaliacaoController {

    private final AvaliacaoServices avaliacaoServices;

    public AvaliacaoController(AvaliacaoServices avaliacaoServices){
        this.avaliacaoServices = avaliacaoServices;
    }

    @GetMapping("/")
    public ResponseEntity<List<AvaliacaoResponseDTO>> findAll(){
        List<AvaliacaoResponseDTO> avaliacaoResponseDTOList = avaliacaoServices.findAll();
        return ResponseEntity.ok(avaliacaoResponseDTOList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AvaliacaoResponseDTO> findById(
            @PathVariable Long id
    ){
        return ResponseEntity.ok(avaliacaoServices.findById(id));
    }

    @PostMapping("/")
    public ResponseEntity<AvaliacaoResponseDTO> save(
            @RequestBody AvaliacaoCreateDTO dto
    ){
        AvaliacaoResponseDTO avaliacaoResponseDTO = avaliacaoServices.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(avaliacaoResponseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AvaliacaoResponseDTO> update(
            @PathVariable Long id,
            @RequestBody AvaliacaoUpdateDTO dto
            ){
        AvaliacaoResponseDTO avaliacaoResponseDTO =  avaliacaoServices.update(id, dto);
        return ResponseEntity.ok(avaliacaoResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ){
        avaliacaoServices.delete(id);
        return ResponseEntity.noContent().build();
    }
}
