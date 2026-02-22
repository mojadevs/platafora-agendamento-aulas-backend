package com.api.demo.model;

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

    @Column(name = "tipo_documento")
    private String tipoDocumento;

}
