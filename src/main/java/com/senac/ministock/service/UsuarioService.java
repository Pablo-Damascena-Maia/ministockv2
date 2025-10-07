package com.senac.ministock.service;

import com.senac.ministock.config.SecurityConfiguration;
import com.senac.ministock.dto.request.UsuarioDTORequest;
import com.senac.ministock.dto.request.UsuarioEmailDTORequest;
import com.senac.ministock.dto.response.UsuarioDTOResponse;
import com.senac.ministock.dto.response.UsuarioDTOUpdateResponse;
import com.senac.ministock.dto.response.UsuarioEmailDTOResponse;
import com.senac.ministock.entity.Role;
import com.senac.ministock.entity.RoleName;
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

import java.util.ArrayList;
import java.util.List;

@Service
public class    UsuarioService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenService jwtTokenService;
    @Autowired
    private SecurityConfiguration securityConfiguration;

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
    public UsuarioDTOResponse criarUsuario(UsuarioDTORequest usuarioDTORequest) {
        List<Role> roles = new ArrayList<Role>();
        Role role = null;
        for (int i = 0; i < usuarioDTORequest.getRolesList().size(); i++) {
            role = new Role();
            role.setName(RoleName.valueOf(usuarioDTORequest.getRolesList().get(i)));
            roles.add(role);
        }

        Usuario usuario = new Usuario();
        usuario.setNome(usuarioDTORequest.getNome());
        usuario.setEmail(usuarioDTORequest.getEmail());
        usuario.setPerfil(usuarioDTORequest.getPerfil());
        usuario.setSenhaHash(securityConfiguration.passwordEncoder().encode(usuarioDTORequest.getSenha()));
        usuario.setStatus(usuarioDTORequest.getStatus());
        usuario.setRoles(List.of(role));
        Usuario usuarioSave = this.usuarioRepository.save(usuario);
        UsuarioDTOResponse usuarioDTOResponse = modelMapper.map(usuarioSave, UsuarioDTOResponse.class);
        return usuarioDTOResponse;
    }
//    public UsuarioDTOResponse criarUsuario(UsuarioDTORequest dto) {
//        if (usuarioRepository.existsByEmail(dto.getEmail())) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "E-mail já cadastrado");
//        }
//
//
//        Usuario usuario = modelMapper.map(dto, Usuario.class);
//        usuario.setSenhaHash(HashUtil.gerarHash(dto.getSenha()));
//        Usuario salvo = usuarioRepository.save(usuario);
//        return modelMapper.map(salvo, UsuarioDTOResponse.class);
//    }


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

    public UsuarioEmailDTOResponse email(UsuarioEmailDTORequest usuarioEmailDTORequest) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(usuarioEmailDTORequest.getEmail(), usuarioEmailDTORequest.getSenha());

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        UsuarioDetailsImpl userDetails = (UsuarioDetailsImpl) authentication.getPrincipal();

        UsuarioEmailDTOResponse usuarioEmailDTOResponse = new UsuarioEmailDTOResponse();
        usuarioEmailDTOResponse.setId(userDetails.getIdUsuario());
        usuarioEmailDTOResponse.setNome(userDetails.getNomeUsuario());
        usuarioEmailDTOResponse.setToken(jwtTokenService.generateToken(userDetails));
        return usuarioEmailDTOResponse;
    }
}
