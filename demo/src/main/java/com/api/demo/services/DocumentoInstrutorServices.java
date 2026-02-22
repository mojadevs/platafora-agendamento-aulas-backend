package com.api.demo.services;

import com.api.demo.enums.documentoInstrutor.Status;
import com.api.demo.enums.documentoInstrutor.TipoArquivo;
import com.api.demo.model.DocumentoInstrutor;
import com.api.demo.model.Instrutor;
import com.api.demo.repository.DocumentoInstrutorRepository;
import com.api.demo.repository.InstrutorRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class DocumentoInstrutorServices {
    private final Cloudinary cloudinary;
    private final DocumentoInstrutorRepository documentoInstrutorRepository;
    private final InstrutorRepository instrutorRepository;


    public DocumentoInstrutorServices(Cloudinary cloudinary, DocumentoInstrutorRepository documentoInstrutorRepository, InstrutorRepository instrutorRepository){
        this.cloudinary = cloudinary;
        this.documentoInstrutorRepository = documentoInstrutorRepository;
        this.instrutorRepository = instrutorRepository;
    }

    public DocumentoInstrutor uploadDocumento(MultipartFile file, String pasta, Long idInstrutor) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
                "folder", pasta
        ));

        TipoArquivo tipoArquivo = fromContentType(file.getContentType());
        String publicId = uploadResult.get("public_id").toString();
        String url = uploadResult.get("secure_url").toString();
        String nomeArquivo = file.getOriginalFilename();
        Instrutor instrutor = findInstrutor(idInstrutor);
        Status status = Status.PENDENTE;
        long tamanhoBytes = file.getSize();

        DocumentoInstrutor documentoInstrutor = new DocumentoInstrutor();
        documentoInstrutor.setNomeArquivo(nomeArquivo);
        documentoInstrutor.setTipoArquivo(tipoArquivo);
        documentoInstrutor.setTamanhoBytes(tamanhoBytes);
        documentoInstrutor.setStatus(status);
        documentoInstrutor.setUrlArquivo(url);
        documentoInstrutor.setInstrutor(instrutor);
        documentoInstrutor.setPublicId(publicId);

        return documentoInstrutorRepository.save(documentoInstrutor);
    }

    private Instrutor findInstrutor(Long idInstrutor){
        Instrutor instrutor = instrutorRepository.findById(idInstrutor).orElseThrow(() -> {
            throw new RuntimeException("Erro : Instrutor não encontrado");
        });

        return instrutor;
    }

    private TipoArquivo fromContentType(String contentType){
        if(contentType == null){
            throw new RuntimeException("Erro : Tipo de arquivo inválido");
        }

        switch (contentType){
            case "application/pdf":
                return TipoArquivo.PDF;
            case "application/vnd.openxmlformats-officedocument.wordprocessingml.document":
                return TipoArquivo.DOCX;
            case "image/png":
                return TipoArquivo.IMG;
            default:
                throw new IllegalArgumentException("Tipo não suportado: " + contentType);
        }
    }
}
