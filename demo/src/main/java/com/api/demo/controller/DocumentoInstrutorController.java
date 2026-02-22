package com.api.demo.controller;

import com.api.demo.model.DocumentoInstrutor;
import com.api.demo.services.DocumentoInstrutorServices;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/documentos-instrutores")
public class DocumentoInstrutorController {
    private final DocumentoInstrutorServices documentoInstrutorServices;

    public DocumentoInstrutorController(DocumentoInstrutorServices documentoInstrutorServices) {
        this.documentoInstrutorServices = documentoInstrutorServices;
    }

    //id do instrutor
    @PostMapping("/upload/{id}")
    public ResponseEntity<DocumentoInstrutor> uploadDocumento(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file
    ) throws IOException {

        String pasta = "instrutores/" + id;

        DocumentoInstrutor documentoInstrutor = documentoInstrutorServices.uploadDocumento(file, pasta, id);

        return ResponseEntity.ok(documentoInstrutor);
    }

    //id do instrutor
    @GetMapping("/instrutores/{id}")
    public ResponseEntity<List<DocumentoInstrutor>> findByInstrutor(
            @PathVariable Long id
    ) {
        List<DocumentoInstrutor> documentoInstrutorList = documentoInstrutorServices.findByInstrutor(id);

        return ResponseEntity.ok(documentoInstrutorList);
    }
}
