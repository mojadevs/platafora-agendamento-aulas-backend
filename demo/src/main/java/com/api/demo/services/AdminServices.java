package com.api.demo.services;

import com.api.demo.dto.admin.AdminCreateDTO;
import com.api.demo.dto.admin.AdminResponseDTO;
import com.api.demo.dto.aluno.AlunoResponseDTO;
import com.api.demo.enums.usuario.Role;
import com.api.demo.jwt.JwtServices;
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
    private final UsuarioServices usuarioServices;
    private final JwtServices jwtServices;

    public AdminServices(AdminRepository adminRepository, AdminMapper adminMapper, UsuarioServices usuarioServices, JwtServices jwtServices){
        this.adminMapper = adminMapper;
        this.usuarioServices = usuarioServices;
        this.adminRepository = adminRepository;
        this.jwtServices = jwtServices;
    }

    public AdminResponseDTO findByUsuario(Usuario usuario){
        Admin admin = adminRepository.findByUsuario(usuario).orElseThrow(() -> new RuntimeException("Administrador não encontrado"));
        AdminResponseDTO adminResponseDTO = adminMapper.toDto(admin);
        return adminResponseDTO;
    }

    public AdminResponseDTO save(AdminCreateDTO dto){
        Role role = Role.ROLE_ADMIN;
        String email = dto.getEmail();
        String senha = dto.getSenha();

        Usuario usuario = usuarioServices.save(email, role, senha);

        Admin admin = adminMapper.toEntity(dto);
        admin.setUsuario(usuario);

        Admin adminSalvo = adminRepository.save(admin); // aqui gera o ID

        System.out.println(adminSalvo.getIdAdmin());
        AdminResponseDTO adminResponseDTO = adminMapper.toDto(adminSalvo);


        String token = jwtServices.generateToken(email, role.name());
        adminResponseDTO.setToken(token);

        adminResponseDTO.setIdUsuario(usuario.getId());
        adminResponseDTO.setEmail(email);

        return adminResponseDTO;
    }
}
