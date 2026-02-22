package com.api.demo.controller;

import com.api.demo.model.DocumentoInstrutor;
import com.api.demo.services.DocumentoInstrutorServices;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/documentos-instrutores")
public class DocumentoInstrutorController {
    private final DocumentoInstrutorServices documentoInstrutorServices;

    public DocumentoInstrutorController(DocumentoInstrutorServices documentoInstrutorServices){
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
}

