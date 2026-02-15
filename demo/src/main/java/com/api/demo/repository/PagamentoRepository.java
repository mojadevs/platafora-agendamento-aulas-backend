package com.api.demo.repository;

import com.api.demo.model.Aluno;
import com.api.demo.model.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
    Optional<Pagamento> findByPaymentIntentId(String paymentIntentId);
}
