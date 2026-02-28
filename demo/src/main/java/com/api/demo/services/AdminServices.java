package com.api.demo.services;

import com.api.demo.dto.admin.AdminResponseDTO;
import com.api.demo.dto.aluno.AlunoResponseDTO;
import com.api.demo.mapper.AdminMapper;
import com.api.demo.model.Admin;
import com.api.demo.model.Aluno;
import com.api.demo.model.Usuario;
import com.api.demo.repository.AdminRepository;
import org.springframework.stereotype.Service;

@Service
public class AdminServices {
    private final AdminRepository adminRepository;
    private final AdminMapper adminMapper;

    public AdminServices(AdminRepository adminRepository, AdminMapper adminMapper){
        this.adminMapper = adminMapper;
        this.adminRepository = adminRepository;
    }

    public AdminResponseDTO findByUsuario(Usuario usuario){
        Admin admin = adminRepository.findByUsuario(usuario).orElseThrow(() -> new RuntimeException("Administrador não encontrado"));
        AdminResponseDTO adminResponseDTO = adminMapper.toDto(admin);
        return adminResponseDTO;
    }
}
