package com.api.demo.repository;

import com.api.demo.model.Aula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AulaRepository extends JpaRepository<Aula, Long> {
    List<Aula> findByInstrutorIdAndStatus(Long idInstrutor, String status);
}
