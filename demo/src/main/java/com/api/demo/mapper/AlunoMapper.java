package com.api.demo.mapper;
import com.api.demo.dto.aluno.AlunoCreateDTO;
import com.api.demo.dto.aluno.AlunoResponseDTO;
import com.api.demo.dto.aluno.AlunoUpdateDTO;
import com.api.demo.model.Aluno;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AlunoMapper {
    Aluno toEntity(AlunoCreateDTO dto);
    AlunoResponseDTO toDto(Aluno aluno);
    void updateEntityFromDTO(AlunoUpdateDTO dto, @MappingTarget Aluno aluno);
}
