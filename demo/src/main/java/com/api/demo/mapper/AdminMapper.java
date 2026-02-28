package com.api.demo.mapper;

import com.api.demo.dto.admin.AdminCreateDTO;
import com.api.demo.dto.admin.AdminResponseDTO;
import com.api.demo.model.Admin;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AdminMapper {
    Admin toEntity(AdminCreateDTO dto);
    AdminResponseDTO toDto(Admin admin);
}
