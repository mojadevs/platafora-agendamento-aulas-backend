package com.api.demo.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Avaliacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_avaliacao")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "aula_id_aula")
    private Aula aula;

    private Integer nota;
    private String comentario;
    private LocalDate data_avaliacao;

    public Aula getAula() {
        return aula;
    }

    public void setAula(Aula aula) {
        this.aula = aula;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public LocalDate getData_avaliacao() {
        return data_avaliacao;
    }

    public void setData_avaliacao(LocalDate data_avaliacao) {
        this.data_avaliacao = data_avaliacao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }
}
