package com.api.demo.mapper;
import com.api.demo.dto.usuario.UsuarioDTO;
import com.api.demo.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper{
   UsuarioDTO toDto(Usuario usuario);
}
