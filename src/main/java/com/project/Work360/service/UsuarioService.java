package com.project.Work360.service;



import com.project.Work360.dto.UsuarioRequest;
import com.project.Work360.dto.UsuarioResponse;
import com.project.Work360.mapper.UsuarioMapper;
import com.project.Work360.model.Usuario;
import com.project.Work360.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper = new UsuarioMapper();

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }


    public UsuarioResponse save(UsuarioRequest request) {
        Usuario usuario = usuarioMapper.toEntity(request);

        String senhaCriptografada = passwordEncoder.encode(request.senha());
        usuario.setSenha(senhaCriptografada);

        Usuario salvo = usuarioRepository.save(usuario);
        return usuarioMapper.toResponse(salvo);
    }

    public Page<UsuarioResponse> findAll(Pageable pageable) {
        return usuarioRepository.findAll(pageable).map(usuarioMapper::toResponse);
    }

    public Usuario findUsuarioById(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public UsuarioResponse findById(Long id) {
        return usuarioRepository.findById(id)
                .map(usuarioMapper::toResponse)
                .orElse(null);
    }


    public UsuarioResponse update(UsuarioRequest request, Long id) {
        Optional<Usuario> optional = usuarioRepository.findById(id);

        if (optional.isPresent()) {
            Usuario usuario = optional.get();

            usuario.setNome(request.nome());
            usuario.setEmail(request.email());


            Usuario atualizado = usuarioRepository.save(usuario);
            return usuarioMapper.toResponse(atualizado);
        }

        return null;
    }

    public boolean delete(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isPresent()) {
            usuarioRepository.delete(usuario.get());
            return true;
        }
        return false;
    }
}
