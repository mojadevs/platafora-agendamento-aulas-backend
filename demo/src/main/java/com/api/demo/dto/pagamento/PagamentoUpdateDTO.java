package com.api.demo.dto.pagamento;

import com.api.demo.model.Aula;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;

public class PagamentoUpdateDTO {
    private Long idAula;
    private String status;
    private Double valorPlataforma;
    private Double valorInstrutor;
    private String metodoPagamento;
    private LocalDate dataCriacao;
    private LocalDate dataConfirmacao;

    public Long getIdAula() {
        return idAula;
    }

    public void setIdAula(Long idAula) {
        this.idAula = idAula;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(String metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
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
