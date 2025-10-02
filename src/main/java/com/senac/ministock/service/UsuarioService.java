package com.senac.ministock.service;

import com.senac.ministock.dto.request.UsuarioDTORequest;
import com.senac.ministock.dto.request.UsuarioEmailDTORequest;
import com.senac.ministock.dto.response.UsuarioDTOResponse;
import com.senac.ministock.dto.response.UsuarioDTOUpdateResponse;
import com.senac.ministock.dto.response.UsuarioEmailDTOResponse;
import com.senac.ministock.entity.Usuario;
import com.senac.ministock.repository.UsuarioRepository;
import com.senac.ministock.util.HashUtil;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class    UsuarioService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenService jwtTokenService;

    private final UsuarioRepository usuarioRepository;
    private final ModelMapper modelMapper;

    public UsuarioService(UsuarioRepository usuarioRepository, ModelMapper modelMapper) {
        this.usuarioRepository = usuarioRepository;
        this.modelMapper = modelMapper;
    }

    public List<UsuarioDTOResponse> listarUsuarios() {
        return usuarioRepository.listarUsuarios()
                .stream()
                .map(u -> modelMapper.map(u, UsuarioDTOResponse.class))
                .toList();
    }

    public UsuarioDTOResponse listarPorId(Integer id) {
        Usuario u = usuarioRepository.obterUsuarioPeloId(id);
        if (u == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado");
        }
        return modelMapper.map(u, UsuarioDTOResponse.class);
    }

    @Transactional
    public UsuarioDTOResponse criarUsuario(UsuarioDTORequest dto) {
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "E-mail já cadastrado");
        }


        Usuario usuario = modelMapper.map(dto, Usuario.class);
        usuario.setSenhaHash(HashUtil.gerarHash(dto.getSenha()));
        Usuario salvo = usuarioRepository.save(usuario);
        return modelMapper.map(salvo, UsuarioDTOResponse.class);
    }

    @Transactional
    public UsuarioDTOResponse atualizarUsuario(Integer id, UsuarioDTORequest dto) {
        Usuario usuario = usuarioRepository.obterUsuarioPeloId(id);
        if (usuario == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário não encontrado");
        }

        if (!usuario.getEmail().equals(dto.getEmail()) && usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "E-mail já cadastrado");
        }


        modelMapper.map(dto, usuario);

        if (dto.getSenha() != null && !dto.getSenha().isBlank()) {
            usuario.setSenhaHash(HashUtil.gerarHash(dto.getSenha()));
        }

        Usuario salvo = usuarioRepository.save(usuario);
        return modelMapper.map(salvo, UsuarioDTOResponse.class);
    }

    @Transactional
    public UsuarioDTOUpdateResponse atualizarStatusUsuario(Integer id, UsuarioDTORequest dto) {
        Usuario usuario = usuarioRepository.obterUsuarioPeloId(id);
        if (usuario == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário não encontrado");
        }

        usuario.setStatus(dto.getStatus());
        Usuario salvo = usuarioRepository.save(usuario);
        return modelMapper.map(salvo, UsuarioDTOUpdateResponse.class);
    }

    @Transactional
    public void apagarUsuario(Integer id) {
        Usuario usuario = usuarioRepository.obterUsuarioPeloId(id);
        if (usuario == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado");
        }
        usuarioRepository.apagadoLogicoUsuario(id);
    }

    public UsuarioEmailDTORequest email(UsuarioEmailDTORequest usuarioEmailDTORequest) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(usuarioEmailDTORequest.getEmail(), usuarioEmailDTORequest.getSenha());

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        UsuarioDetailsImpl userDetails = (UsuarioDetailsImpl) authentication.getPrincipal();

        UsuarioEmailDTOResponse usuarioEmailDTOResponse = new UsuarioEmailDTOResponse();
        usuarioEmailDTOResponse.setId(userDetails.getIdUsuario());
        usuarioEmailDTOResponse.setNome(userDetails.getNomeUsuario());
        usuarioEmailDTOResponse.setToken(jwtTokenService.generateToken(userDetails));
        return usuarioEmailDTORequest;
    }
}
