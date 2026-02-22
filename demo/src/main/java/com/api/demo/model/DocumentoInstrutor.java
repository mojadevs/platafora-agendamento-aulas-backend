package com.api.demo.model;

import com.api.demo.enums.documentoInstrutor.Status;
import com.api.demo.enums.documentoInstrutor.TipoArquivo;
import jakarta.persistence.*;

@Entity
public class DocumentoInstrutor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_documento_instrutor")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_instrutor")
    private Instrutor instrutor;

    @Column(name = "nome_arquivo")
    private String nomeArquivo;

    @Column(name = "tamanho_bytes")
    private Long tamanhoBytes;

    private Status status;

    @Column(name = "motivo_recusa")
    private String motivoRecusa;

    @Column(name = "url_arquivo")
    private String urlArquivo;

    @Column(name = "public_id")
    private String publicId;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipoArquivo")
    private TipoArquivo tipoArquivo;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public Instrutor getInstrutor() {
        return instrutor;
    }

    public void setInstrutor(Instrutor instrutor) {
        this.instrutor = instrutor;
    }

    public String getMotivoRecusa() {
        return motivoRecusa;
    }

    public void setMotivoRecusa(String motivoRecusa) {
        this.motivoRecusa = motivoRecusa;
    }

    public String getPublicId() {
        return publicId;
    }

    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getTamanhoBytes() {
        return tamanhoBytes;
    }

    public void setTamanhoBytes(Long tamanhoBytes) {
        this.tamanhoBytes = tamanhoBytes;
    }

    public TipoArquivo getTipoArquivo() {
        return tipoArquivo;
    }

    public void setTipoArquivo(TipoArquivo tipoArquivo) {
        this.tipoArquivo = tipoArquivo;
    }

    public String getUrlArquivo() {
        return urlArquivo;
    }

    public void setUrlArquivo(String urlArquivo) {
        this.urlArquivo = urlArquivo;
    }
}
