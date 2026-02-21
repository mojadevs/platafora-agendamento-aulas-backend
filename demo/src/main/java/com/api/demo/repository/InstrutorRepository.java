package com.api.demo.repository;

import com.api.demo.model.Instrutor;
import com.api.demo.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InstrutorRepository extends JpaRepository<Instrutor, Long> {
    Optional<Instrutor> findByUsuario(Usuario usuario);
}
