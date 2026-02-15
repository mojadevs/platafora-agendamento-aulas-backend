package com.api.demo.mapper;

import com.api.demo.dto.aluno.AlunoUpdateDTO;
import com.api.demo.dto.aula.AulaCreateDTO;
import com.api.demo.dto.aula.AulaResponseDTO;
import com.api.demo.dto.aula.AulaUpdateDTO;
import com.api.demo.model.Aluno;
import com.api.demo.model.Aula;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AulaMapper {
    Aula toEntity(AulaCreateDTO dto);
    @Mapping(source = "instrutor.id", target = "idInstrutor")
    @Mapping(source = "aluno.id", target = "idAluno")
    AulaResponseDTO toDto(Aula aula);
    @Mapping(target = "instrutor", ignore = true)
    @Mapping(target = "aluno", ignore = true)
    void updateEntityFromDTO(AulaUpdateDTO dto, @MappingTarget Aula aula);
}
