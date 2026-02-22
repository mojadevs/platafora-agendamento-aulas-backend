package com.api.demo.services;

import com.api.demo.dto.aluno.AlunoCreateDTO;
import com.api.demo.dto.aluno.AlunoResponseDTO;
import com.api.demo.dto.aluno.AlunoUpdateDTO;
import com.api.demo.dto.usuario.UsuarioDTO;
import com.api.demo.enums.Role;
import com.api.demo.jwt.JwtServices;
import com.api.demo.mapper.AlunoMapper;
import com.api.demo.model.Aluno;
import com.api.demo.model.Usuario;
import com.api.demo.repository.AlunoRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AlunoServices {

    private final AlunoRepository alunoRepository;
    private final AlunoMapper alunoMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtServices jwtServices;
    private final UsuarioServices usuarioServices;

    public AlunoServices(UsuarioServices usuarioServices,JwtServices jwtServices, AlunoRepository alunoRepository, AlunoMapper alunoMapper, PasswordEncoder passwordEncoder){
        this.alunoRepository = alunoRepository;
        this.alunoMapper = alunoMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtServices = jwtServices;
        this.usuarioServices = usuarioServices;
    }

    public List<AlunoResponseDTO> findAll(){
        List<Aluno> alunos = alunoRepository.findAll();
        List<AlunoResponseDTO> alunoResponseDTOList = new ArrayList<>();

        for(Aluno aluno : alunos){
            System.out.println(aluno.getNome());
            alunoResponseDTOList.add(alunoMapper.toDto(aluno));
        }


        return alunoResponseDTOList;
    }

    public AlunoResponseDTO findById(Long id){
        Aluno aluno = alunoRepository.findById(id).orElseThrow(() -> {
            return new RuntimeException("Aluno não encontrado");
        });

        AlunoResponseDTO alunoResponseDTO = alunoMapper.toDto(aluno);

        return alunoResponseDTO;
    }

    public void delete(long id){
        Aluno aluno = alunoRepository.findById(id).orElseThrow(() -> {
            return new RuntimeException("Aluno não encontrado");
        });

        alunoRepository.delete(aluno);
    }

    @Transactional
    public AlunoResponseDTO save(AlunoCreateDTO dto){
        Role role = Role.ALUNO;
        String email = dto.getEmail();
        String senha = dto.getSenha();

        Usuario usuario = usuarioServices.save(email, role, senha);
        Aluno aluno = alunoMapper.toEntity(dto);
        aluno.setUsuario(usuario);
        AlunoResponseDTO alunoResponseDTO = alunoMapper.toDto(alunoRepository.save(aluno));
        String token = jwtServices.generateToken(email);
        alunoResponseDTO.setToken(token);
        alunoResponseDTO.setIdUsuario(usuario.getId());
        alunoResponseDTO.setEmail(email);

        return alunoResponseDTO;
    }

    public AlunoResponseDTO findByUsuario(Usuario usuario){
        Aluno aluno = alunoRepository.findByUsuario(usuario).orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
        AlunoResponseDTO alunoResponseDTO = alunoMapper.toDto(aluno);
        return alunoResponseDTO;
    }

    public AlunoResponseDTO update(Long id, AlunoUpdateDTO dto){
        Aluno aluno = alunoRepository.findById(id).orElseThrow(()-> {
            return new RuntimeException("Aluno não encontrado");
        });

        alunoMapper.updateEntityFromDTO(dto, aluno);

        return alunoMapper.toDto(alunoRepository.save(aluno));
    }
}
