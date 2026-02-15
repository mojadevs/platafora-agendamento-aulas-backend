package com.api.demo.services;
import com.api.demo.dto.login.LoginDTO;
import com.api.demo.dto.login.LoginResponseDTO;
import com.api.demo.jwt.JwtServices;
import com.api.demo.model.Aluno;
import com.api.demo.model.Instrutor;
import com.api.demo.repository.AlunoRepository;
import com.api.demo.repository.InstrutorRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginServices {
    private final AlunoRepository alunoRepository;
    private final InstrutorRepository instrutorRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtServices jwtServices;

    public LoginServices(AlunoRepository alunoRepository, InstrutorRepository instrutorRepository, PasswordEncoder passwordEncoder, JwtServices jwtServices){
        this.alunoRepository = alunoRepository;
        this.instrutorRepository = instrutorRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtServices = jwtServices;
    }

    public LoginResponseDTO login(LoginDTO dto){
        Optional<Aluno> alunoOptional = alunoRepository.findByEmail(dto.getEmail());

        if(alunoOptional.isPresent()){

            Aluno aluno = alunoOptional.get();
            if(passwordEncoder.matches(dto.getSenha(), aluno.getSenha())){

                String token = jwtServices.generateToken(aluno.getEmail());
                LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
                loginResponseDTO.setId(aluno.getId_aluno());
                loginResponseDTO.setNome(aluno.getNome());
                loginResponseDTO.setToken(token);
                loginResponseDTO.setRole("ALUNO");

                return loginResponseDTO;
            }
        }

        Optional<Instrutor> instrutorOptional = instrutorRepository.findByEmail(dto.getEmail());
        if(instrutorOptional.isPresent()){
            Instrutor instrutor = instrutorOptional.get();
            if(passwordEncoder.matches(dto.getSenha(), instrutor.getSenha())){
                String token = jwtServices.generateToken(instrutor.getEmail());
                LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
                loginResponseDTO.setId(instrutor.getId_instrutor());
                loginResponseDTO.setNome(instrutor.getNome());
                loginResponseDTO.setToken(token);
                loginResponseDTO.setRole("INSTRUTOR");

                return loginResponseDTO;
            }
        }
        throw new RuntimeException("Erro de autenticação");
    }
}
