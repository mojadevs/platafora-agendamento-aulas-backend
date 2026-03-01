package com.api.demo.services;
import com.api.demo.dto.admin.AdminResponseDTO;
import com.api.demo.dto.aluno.AlunoResponseDTO;
import com.api.demo.dto.instrutor.InstrutorResponseDTO;
import com.api.demo.dto.login.LoginDTO;
import com.api.demo.dto.login.LoginResponseDTO;
import com.api.demo.enums.usuario.Role;
import com.api.demo.jwt.JwtServices;
import com.api.demo.model.Admin;
import com.api.demo.model.Usuario;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginServices {
    private final PasswordEncoder passwordEncoder;
    private final JwtServices jwtServices;
    private final UsuarioServices usuarioServices;
    private final AlunoServices alunoServices;
    private final AdminServices adminServices;
    private final InstrutorServices instrutorServices;

    public LoginServices(AdminServices adminServices, AlunoServices alunoServices, InstrutorServices instrutorServices, UsuarioServices usuarioServices, PasswordEncoder passwordEncoder, JwtServices jwtServices){
        this.passwordEncoder = passwordEncoder;
        this.jwtServices = jwtServices;
        this.usuarioServices = usuarioServices;
        this.instrutorServices = instrutorServices;
        this.alunoServices = alunoServices;
        this.adminServices = adminServices;
    }

    public LoginResponseDTO login(LoginDTO dto){
        Usuario usuario = usuarioServices.findByEmail(dto.getEmail());

        if (!passwordEncoder.matches(dto.getSenha(), usuario.getSenha())) {
            throw new RuntimeException("Credenciais inválidas");
        }

        String token = jwtServices.generateToken(usuario.getEmail(), usuario.getRole().name());

        LoginResponseDTO response = new LoginResponseDTO();
        response.setToken(token);
        response.setRole(usuario.getRole().name());

        if (usuario.getRole() == Role.ROLE_ALUNO) {
            AlunoResponseDTO alunoResponseDTO = alunoServices.findByUsuario(usuario);
            response.setId(alunoResponseDTO.getId());
            response.setNome(alunoResponseDTO.getNome());

        } else if (usuario.getRole() == Role.ROLE_INSTRUTOR) {
            InstrutorResponseDTO instrutorResponseDTO = instrutorServices.findByUsuario(usuario);

            if (!instrutorResponseDTO.getAtivo()) {
                throw new RuntimeException("Instrutor ainda não aprovado");
            }

            response.setId(instrutorResponseDTO.getId());
            response.setNome(instrutorResponseDTO.getNome());
        }else if (usuario.getRole() == Role.ROLE_ADMIN){
            AdminResponseDTO adminResponseDTO = adminServices.findByUsuario(usuario);

            response.setId(adminResponseDTO.getIdAdmin());
            response.setNome(adminResponseDTO.getNome());
        }

        return response;
    }
}
