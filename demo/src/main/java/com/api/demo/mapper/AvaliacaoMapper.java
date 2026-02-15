package com.api.demo.mapper;

import com.api.demo.dto.aula.AulaUpdateDTO;
import com.api.demo.dto.avaliacao.AvaliacaoCreateDTO;
import com.api.demo.dto.avaliacao.AvaliacaoResponseDTO;
import com.api.demo.dto.avaliacao.AvaliacaoUpdateDTO;
import com.api.demo.model.Aula;
import com.api.demo.model.Avaliacao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AvaliacaoMapper {
    @Mapping(source = "aula.id", target = "idAula")
    @Mapping(source = "data_avaliacao", target = "dataAvaliacao")
    AvaliacaoResponseDTO toDto(Avaliacao avaliacao);

    @Mapping(source = "idAula", target = "aula.id")
    @Mapping(source = "dataAvaliacao", target = "data_avaliacao")
    Avaliacao toEntity(AvaliacaoCreateDTO dto);
    @Mapping(target = "aula", ignore = true)
    void updateEntityFromDTO(AvaliacaoUpdateDTO dto, @MappingTarget Avaliacao avaliacao);
}
