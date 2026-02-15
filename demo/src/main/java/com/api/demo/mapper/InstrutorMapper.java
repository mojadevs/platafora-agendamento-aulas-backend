package com.api.demo.mapper;

import com.api.demo.dto.instrutor.InstrutorCreateDTO;
import com.api.demo.dto.instrutor.InstrutorResponseDTO;
import com.api.demo.dto.instrutor.InstrutorUpdateDTO;
import com.api.demo.model.Avaliacao;
import com.api.demo.model.Instrutor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface InstrutorMapper {
    Instrutor toEntity(InstrutorCreateDTO dto);
    InstrutorResponseDTO toDto(Instrutor instrutor);
    void updateEntityFromDTO(InstrutorUpdateDTO dto, @MappingTarget Instrutor instrutor);
}
