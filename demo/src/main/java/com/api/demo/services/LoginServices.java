package com.api.demo.services;
import com.api.demo.dto.aluno.AlunoResponseDTO;
import com.api.demo.dto.instrutor.InstrutorResponseDTO;
import com.api.demo.dto.login.LoginDTO;
import com.api.demo.dto.login.LoginResponseDTO;
import com.api.demo.dto.usuario.UsuarioDTO;
import com.api.demo.enums.Role;
import com.api.demo.jwt.JwtServices;
import com.api.demo.model.Aluno;
import com.api.demo.model.Instrutor;
import com.api.demo.model.Usuario;
import com.api.demo.repository.AlunoRepository;
import com.api.demo.repository.InstrutorRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginServices {
    private final PasswordEncoder passwordEncoder;
    private final JwtServices jwtServices;
    private final UsuarioServices usuarioServices;
    private final AlunoServices alunoServices;
    private final InstrutorServices instrutorServices;

    public LoginServices(AlunoServices alunoServices, InstrutorServices instrutorServices, UsuarioServices usuarioServices, PasswordEncoder passwordEncoder, JwtServices jwtServices){
        this.passwordEncoder = passwordEncoder;
        this.jwtServices = jwtServices;
        this.usuarioServices = usuarioServices;
        this.instrutorServices = instrutorServices;
        this.alunoServices = alunoServices;
    }

    public LoginResponseDTO login(LoginDTO dto){

        Usuario usuario = usuarioServices.findByEmail(dto.getEmail());

        if (!passwordEncoder.matches(dto.getSenha(), usuario.getSenha())) {
            throw new RuntimeException("Credenciais inválidas");
        }

        String token = jwtServices.generateToken(usuario.getEmail());

        LoginResponseDTO response = new LoginResponseDTO();
        response.setToken(token);
        response.setRole(usuario.getRole());

        if (usuario.getRole() == Role.ALUNO) {
            AlunoResponseDTO alunoResponseDTO = alunoServices.findByUsuario(usuario);
            response.setId(alunoResponseDTO.getId());
            response.setNome(alunoResponseDTO.getNome());

        } else if (usuario.getRole() == Role.INSTRUTOR) {

            InstrutorResponseDTO instrutorResponseDTO = instrutorServices.findByUsuario(usuario);

            if (!instrutorResponseDTO.getAtivo()) {
                throw new RuntimeException("Instrutor ainda não aprovado");
            }

            response.setId(instrutorResponseDTO.getId());
            response.setNome(instrutorResponseDTO.getNome());
        }

        return response;
    }
}
