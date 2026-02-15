package com.api.demo.services;
import com.api.demo.dto.aula.AulaCreateDTO;
import com.api.demo.dto.aula.AulaResponseDTO;
import com.api.demo.dto.aula.AulaUpdateDTO;
import com.api.demo.mapper.AulaMapper;
import com.api.demo.model.Aluno;
import com.api.demo.model.Aula;
import com.api.demo.model.Instrutor;
import com.api.demo.repository.AlunoRepository;
import com.api.demo.repository.AulaRepository;
import com.api.demo.repository.InstrutorRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AulaServices {

    private final AulaRepository aulaRepository;
    private final AulaMapper aulaMapper;
    private final InstrutorRepository instrutorRepository;
    private final AlunoRepository alunoRepository;

    public AulaServices(AulaRepository aulaRepository, AulaMapper aulaMapper, AlunoRepository alunoRepository, InstrutorRepository instrutorRepository)
    {
        this.aulaRepository = aulaRepository;
        this.aulaMapper = aulaMapper;
        this.instrutorRepository = instrutorRepository;
        this.alunoRepository = alunoRepository;
    }

    public List<AulaResponseDTO> findAll(){
        List<Aula> aulas = aulaRepository.findAll();
        List<AulaResponseDTO> aulaResponseDTOList = new ArrayList<>();

        for(Aula aula : aulas){
            aulaResponseDTOList.add(aulaMapper.toDto(aula));
        }

        return aulaResponseDTOList;
    }

    public AulaResponseDTO findById(Long id){
        Aula aula = aulaRepository.findById(id).orElseThrow(() -> {
            return new RuntimeException("Aula não encontrada");
        });

        AulaResponseDTO aulaResponseDTO = aulaMapper.toDto(aula);

        return aulaResponseDTO;
    }

    public void delete(long id){
        Aula aula = aulaRepository.findById(id).orElseThrow(() -> {
            return new RuntimeException("Aula não encontrada");
        });

        aulaRepository.delete(aula);
    }

    public AulaResponseDTO save(AulaCreateDTO dto){
        Instrutor instrutor = instrutorRepository.findById(dto.getIdInstrutor()).orElseThrow(() -> new RuntimeException("Instrutor não encontrado"));
        Aluno aluno = alunoRepository.findById(dto.getIdAluno()).orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
        Aula aula = aulaMapper.toEntity(dto);
        aula.setInstrutor(instrutor);
        aula.setAluno(aluno);
        return aulaMapper.toDto(aulaRepository.save(aula));
    }

    public AulaResponseDTO update(Long id, AulaUpdateDTO dto){
        Aula aula = aulaRepository.findById(id).orElseThrow(()-> {
            return new RuntimeException("Aula não encontrado");
        });
        Instrutor instrutor = instrutorRepository.findById(dto.getIdInstrutor()).orElseThrow(() -> new RuntimeException("Instrutor não encontrado"));
        Aluno aluno = alunoRepository.findById(dto.getIdAluno()).orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        aulaMapper.updateEntityFromDTO(dto, aula);

        aula.setInstrutor(instrutor);
        aula.setAluno(aluno);

        return aulaMapper.toDto(aulaRepository.save(aula));
    }
}
