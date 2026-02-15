package com.api.demo.services;
import com.api.demo.dto.instrutor.InstrutorCreateDTO;
import com.api.demo.dto.instrutor.InstrutorResponseDTO;
import com.api.demo.dto.instrutor.InstrutorUpdateDTO;
import com.api.demo.jwt.JwtServices;
import com.api.demo.mapper.InstrutorMapper;
import com.api.demo.model.Instrutor;
import com.api.demo.repository.InstrutorRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InstrutorServices {

    private final InstrutorRepository instrutorRepository;
    private final InstrutorMapper instrutorMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtServices jwtServices;

    public InstrutorServices(JwtServices jwtServices,InstrutorRepository instrutorRepository, InstrutorMapper instrutorMapper, PasswordEncoder passwordEncoder){
        this.instrutorRepository = instrutorRepository;
        this.instrutorMapper = instrutorMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtServices = jwtServices;
    }

    public List<InstrutorResponseDTO> findAll(){
        List<Instrutor> instrutores = instrutorRepository.findAll();
        List<InstrutorResponseDTO> instrutorResponseDTOList = new ArrayList<>();

        for(Instrutor instrutor : instrutores){
            instrutorResponseDTOList.add(instrutorMapper.toDto(instrutor));
        }

        return instrutorResponseDTOList;
    }

    public InstrutorResponseDTO findById(Long id){
        Instrutor instrutor = instrutorRepository.findById(id).orElseThrow(() -> {
            return new RuntimeException("Instrutor não encontrada");
        });

        InstrutorResponseDTO instrutorResponseDTO = instrutorMapper.toDto(instrutor);

        return instrutorResponseDTO;
    }

    public void delete(long id){
        Instrutor instrutor = instrutorRepository.findById(id).orElseThrow(() -> {
            return new RuntimeException("Instrutor não encontrada");
        });

        instrutorRepository.delete(instrutor);
    }

    public InstrutorResponseDTO save(InstrutorCreateDTO dto){
        Instrutor instrutor = instrutorMapper.toEntity(dto);
        instrutor.setSenha(passwordEncoder.encode(instrutor.getSenha()));
        InstrutorResponseDTO instrutorResponseDTO = instrutorMapper.toDto(instrutorRepository.save(instrutor));
        String token = jwtServices.generateToken(instrutor.getEmail());
        instrutorResponseDTO.setToken(token);
        return instrutorResponseDTO;
    }

    public InstrutorResponseDTO update(Long id, InstrutorUpdateDTO dto){
        Instrutor instrutor = instrutorRepository.findById(id).orElseThrow(()-> {
            return new RuntimeException("instrutor não encontrado");
        });

        instrutorMapper.updateEntityFromDTO(dto, instrutor);

        return instrutorMapper.toDto(instrutorRepository.save(instrutor));
    }
}
