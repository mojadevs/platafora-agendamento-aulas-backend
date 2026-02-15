package com.api.demo.mapper;

import com.api.demo.dto.instrutor.InstrutorUpdateDTO;
import com.api.demo.dto.pagamento.PagamentoCreateDTO;
import com.api.demo.dto.pagamento.PagamentoResponseDTO;
import com.api.demo.dto.pagamento.PagamentoUpdateDTO;
import com.api.demo.model.Instrutor;
import com.api.demo.model.Pagamento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PagamentoMapper {

    // Mapeia DTO de criação para entidade
    @Mapping(source = "idAula", target = "aula.id") // se idAula vem do DTO
    Pagamento toEntity(PagamentoCreateDTO dto);

    // Mapeia entidade para DTO de resposta
    @Mapping(source = "aula.id", target = "idAula") // se o DTO precisa de idAula
    PagamentoResponseDTO toDto(Pagamento pagamento);

    // Atualiza entidade existente a partir do DTO de atualização
    @Mapping(source = "idAula", target = "aula.id")
    void updateEntityFromDTO(PagamentoUpdateDTO dto, @MappingTarget Pagamento pagamento);
}