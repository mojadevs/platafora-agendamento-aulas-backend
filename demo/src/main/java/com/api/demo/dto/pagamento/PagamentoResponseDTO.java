package com.api.demo.dto.pagamento;

import com.api.demo.model.Aula;

import java.time.LocalDate;

public class PagamentoResponseDTO {
    private Long id;
    private Long idAula;
    private String paymentIntentId;
    private String status;
    private Double valorPlataforma;
    private Double valorInstrutor;
    private String metodoPagamento;
    private LocalDate dataCriacao;
    private LocalDate dataConfirmacao;
    private String clientSecret;

    public String getPaymentIntentId() {
        return paymentIntentId;
    }

    public void setPaymentIntentId(String paymentIntentId) {
        this.paymentIntentId = paymentIntentId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public Double getValorPlataforma() {
        return valorPlataforma;
    }

    public void setValorPlataforma(Double valorPlataforma) {
        this.valorPlataforma = valorPlataforma;
    }

    public Double getValorInstrutor() {
        return valorInstrutor;
    }

    public void setValorInstrutor(Double valorInstrutor) {
        this.valorInstrutor = valorInstrutor;
    }

    public String getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(String metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDate getDataConfirmacao() {
        return dataConfirmacao;
    }

    public void setDataConfirmacao(LocalDate dataConfirmacao) {
        this.dataConfirmacao = dataConfirmacao;
    }

    public Long getIdAula() {
        return idAula;
    }

    public void setIdAula(Long idAula) {
        this.idAula = idAula;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
