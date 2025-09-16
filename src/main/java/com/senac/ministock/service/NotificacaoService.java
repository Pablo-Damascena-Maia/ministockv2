package com.senac.ministock.service;

import com.senac.ministock.dto.request.NotificacaoDTORequest;
import com.senac.ministock.dto.response.NotificacaoDTOResponse;
import com.senac.ministock.dto.response.NotificacaoDTOUpdateResponse;
import com.senac.ministock.entity.Notificacao;
import com.senac.ministock.entity.Usuario;
import com.senac.ministock.repository.NotificacaoRepository;
import com.senac.ministock.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class NotificacaoService {

    private final NotificacaoRepository notificacaoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ModelMapper modelMapper;

    public NotificacaoService(NotificacaoRepository notificacaoRepository,
                              UsuarioRepository usuarioRepository,
                              ModelMapper modelMapper) {
        this.notificacaoRepository = notificacaoRepository;
        this.usuarioRepository = usuarioRepository;
        this.modelMapper = modelMapper;
    }

    public List<NotificacaoDTOResponse> listarNotificacoes() {
        return notificacaoRepository.listarNotificacoes()
                .stream()
                .map(notificacao -> modelMapper.map(notificacao, NotificacaoDTOResponse.class))
                .toList();
    }

    public NotificacaoDTOResponse listarPorNotificacaoId(Integer notificacaoId) {
        Notificacao notificacao = notificacaoRepository.obterNotificacaoPeloId(notificacaoId);
        return (notificacao != null) ? modelMapper.map(notificacao, NotificacaoDTOResponse.class) : null;
    }

    @Transactional
    public NotificacaoDTOResponse criarNotificacao(NotificacaoDTORequest dto) {
        Usuario usuario = usuarioRepository.obterUsuarioPeloId(dto.getUsuarioId());

        Notificacao notificacao = modelMapper.map(dto, Notificacao.class);
        notificacao.setUsuario(usuario);

        Notificacao notificacaoSalva = notificacaoRepository.save(notificacao);
        return modelMapper.map(notificacaoSalva, NotificacaoDTOResponse.class);
    }

    @Transactional
    public NotificacaoDTOResponse atualizarNotificacao(Integer notificacaoId, NotificacaoDTORequest dto) {
        Notificacao notificacao = notificacaoRepository.findById(notificacaoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Notificação não encontrada"));

        modelMapper.map(dto, notificacao);

        if (dto.getUsuarioId() != null) {
            Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário não encontrado"));
            notificacao.setUsuario(usuario);
        }

        Notificacao notificacaoAtualizada = notificacaoRepository.save(notificacao);
        return modelMapper.map(notificacaoAtualizada, NotificacaoDTOResponse.class);
    }

    @Transactional
    public NotificacaoDTOUpdateResponse atualizarStatusNotificacao(Integer notificacaoId, NotificacaoDTORequest dto) {
        Notificacao notificacao = notificacaoRepository.findById(notificacaoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Notificação não encontrada"));

        notificacao.setStatus(dto.getStatus());

        Notificacao notificacaoAtualizada = notificacaoRepository.save(notificacao);
        return modelMapper.map(notificacaoAtualizada, NotificacaoDTOUpdateResponse.class);
    }

    @Transactional
    public void apagarNotificacao(Integer notificacaoId) {
        Notificacao notificacao = notificacaoRepository.findById(notificacaoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Notificação não encontrada"));

        notificacao.setStatus(-1); // apagado lógico
        notificacaoRepository.save(notificacao);
    }

    @Transactional
    public NotificacaoDTOUpdateResponse marcarComoLida(Integer notificacaoId) {
        Notificacao notificacao = notificacaoRepository.findById(notificacaoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Notificação não encontrada"));

        notificacao.setLida(1); // marca como lida
        Notificacao atualizada = notificacaoRepository.save(notificacao);
        return modelMapper.map(atualizada, NotificacaoDTOUpdateResponse.class);
    }

}
