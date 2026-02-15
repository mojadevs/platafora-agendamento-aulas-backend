package com.api.demo.dto.aula;

import com.api.demo.model.Aluno;
import com.api.demo.model.Instrutor;

import java.time.LocalDate;
import java.time.LocalTime;

public class AulaUpdateDTO {
    private Long idAluno;
    private Long idInstrutor;
    private LocalDate dataHora;
    private LocalTime duracao;
    private String status;
    private Double valorTotal;

    public LocalDate getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDate dataHora) {
        this.dataHora = dataHora;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public LocalTime getDuracao() {
        return duracao;
    }

    public void setDuracao(LocalTime duracao) {
        this.duracao = duracao;
    }

    public Long getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(Long idAluno) {
        this.idAluno = idAluno;
    }

    public Long getIdInstrutor() {
        return idInstrutor;
    }

    public void setIdInstrutor(Long idInstrutor) {
        this.idInstrutor = idInstrutor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
