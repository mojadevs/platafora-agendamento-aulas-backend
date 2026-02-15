package com.api.demo.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pagamento")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "aula_id_aula")
    private Aula aula;

    @Column(name = "paymentIntent_id")
    private String paymentIntentId;

    @Column(name = "status")
    private String status;

    @Column(name = "valor_plataforma")
    private Double valorPlataforma;

    @Column(name = "valor_instrutor")
    private Double valorInstrutor;

    @Column(name = "metodo_pagamento")
    private String metodoPagamento;

    @Column(name = "data_criacao")
    private LocalDate dataCriacao;

    @Column(name = "data_confirmacao")
    private LocalDate dataConfirmacao;

    // getters e setters (CamelCase)

    public Aula getAula() {
        return aula;
    }

    public void setAula(Aula aula) {
        this.aula = aula;
    }

    public LocalDate getDataConfirmacao() {
        return dataConfirmacao;
    }

    public void setDataConfirmacao(LocalDate dataConfirmacao) {
        this.dataConfirmacao = dataConfirmacao;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(String metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    public String getPaymentIntentId() {
        return paymentIntentId;
    }

    public void setPaymentIntentId(String paymentIntentId) {
        this.paymentIntentId = paymentIntentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getValorInstrutor() {
        return valorInstrutor;
    }

    public void setValorInstrutor(Double valorInstrutor) {
        this.valorInstrutor = valorInstrutor;
    }

    public Double getValorPlataforma() {
        return valorPlataforma;
    }

    public void setValorPlataforma(Double valorPlataforma) {
        this.valorPlataforma = valorPlataforma;
    }
}
