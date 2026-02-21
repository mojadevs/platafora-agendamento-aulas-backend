package com.api.demo.services;
import com.api.demo.dto.usuario.UsuarioDTO;
import com.api.demo.enums.Role;
import com.api.demo.mapper.UsuarioMapper;
import com.api.demo.model.Usuario;
import com.api.demo.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServices {
    private final PasswordEncoder passwordEncoder;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;


    public UsuarioServices(PasswordEncoder passwordEncoder, UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper){
        this.passwordEncoder = passwordEncoder;
        this.usuarioMapper = usuarioMapper;
        this.usuarioRepository = usuarioRepository;
    }

    public void delete(String email){
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() -> {
            return new RuntimeException("Usuário não encontrado");
        });

        usuarioRepository.delete(usuario);
    }

    public Usuario findByEmail(String email){
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() -> {
            return new RuntimeException("Usuário não encontrado");
        });

        return usuario;
    }


    public Usuario save(String email, Role role, String senha){
        String senha_criptografada = passwordEncoder.encode(senha);
        Usuario usuario = new Usuario(senha_criptografada, role, email);

        Usuario usuario_save = usuarioRepository.save(usuario);
        return usuario_save;
    }
}

