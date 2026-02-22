package com.api.demo.repository;

import com.api.demo.model.DocumentoInstrutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentoInstrutorRepository extends JpaRepository<DocumentoInstrutor, Long> { }
