package com.api.demo.repository;

import com.api.demo.model.DocumentoInstrutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentoInstrutorRepository extends JpaRepository<DocumentoInstrutor, Long> {
    List<DocumentoInstrutor> findByInstrutorId(Long idInstrutor);
}
