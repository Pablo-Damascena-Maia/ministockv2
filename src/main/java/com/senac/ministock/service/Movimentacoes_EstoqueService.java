package com.senac.ministock.service;

import com.senac.ministock.dto.request.Movimentacoes_EstoqueDTORequest;
import com.senac.ministock.dto.response.Movimentacoes_EstoqueDTOResponse;
import com.senac.ministock.dto.response.Movimentacoes_EstoqueDTOUpdateResponse;
import com.senac.ministock.entity.Movimentacoes_Estoque;
import com.senac.ministock.entity.Produto;
import com.senac.ministock.entity.Usuario;
import com.senac.ministock.repository.Movimentacoes_EstoqueRepository;
import com.senac.ministock.repository.ProdutoRepository;
import com.senac.ministock.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class Movimentacoes_EstoqueService {

    private final Movimentacoes_EstoqueRepository movimentacoesRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProdutoRepository produtoRepository;
    private final ModelMapper modelMapper;

    public Movimentacoes_EstoqueService(Movimentacoes_EstoqueRepository movimentacoesRepository,
                                        UsuarioRepository usuarioRepository,
                                        ProdutoRepository produtoRepository,
                                        ModelMapper modelMapper) {
        this.movimentacoesRepository = movimentacoesRepository;
        this.usuarioRepository = usuarioRepository;
        this.produtoRepository = produtoRepository;
        this.modelMapper = modelMapper;
    }

    public List<Movimentacoes_EstoqueDTOResponse> listarMovimentacoes() {
        return movimentacoesRepository.findAll()
                .stream()
                .map(m -> modelMapper.map(m, Movimentacoes_EstoqueDTOResponse.class))
                .toList();
    }

    public Movimentacoes_EstoqueDTOResponse listarPorId(Integer id) {
        Movimentacoes_Estoque movimentacao = movimentacoesRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Movimentação não encontrada"));
        return modelMapper.map(movimentacao, Movimentacoes_EstoqueDTOResponse.class);
    }

    @Transactional
    public Movimentacoes_EstoqueDTOResponse criarMovimentacao(Movimentacoes_EstoqueDTORequest dto) {
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário não encontrado"));

        Produto produto = produtoRepository.findById(dto.getProdutoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Produto não encontrado"));

        Movimentacoes_Estoque movimentacao = modelMapper.map(dto, Movimentacoes_Estoque.class);
        movimentacao.setUsuario(usuario);
        movimentacao.setProduto(produto);

        Movimentacoes_Estoque salva = movimentacoesRepository.save(movimentacao);
        return modelMapper.map(salva, Movimentacoes_EstoqueDTOResponse.class);
    }

    @Transactional
    public Movimentacoes_EstoqueDTOResponse atualizarMovimentacao(Integer id, Movimentacoes_EstoqueDTORequest dto) {
        Movimentacoes_Estoque movimentacao = movimentacoesRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Movimentação não encontrada"));

        modelMapper.map(dto, movimentacao);

        if (dto.getUsuarioId() != null) {
            Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário não encontrado"));
            movimentacao.setUsuario(usuario);
        }

        if (dto.getProdutoId() != null) {
            Produto produto = produtoRepository.findById(dto.getProdutoId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Produto não encontrado"));
            movimentacao.setProduto(produto);
        }

        Movimentacoes_Estoque atualizada = movimentacoesRepository.save(movimentacao);
        return modelMapper.map(atualizada, Movimentacoes_EstoqueDTOResponse.class);
    }

    @Transactional
    public Movimentacoes_EstoqueDTOUpdateResponse atualizarStatusMovimentacao(Integer id, Movimentacoes_EstoqueDTORequest dto) {
        Movimentacoes_Estoque movimentacao = movimentacoesRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Movimentação não encontrada"));

        movimentacao.setStatus(dto.getStatus());

        Movimentacoes_Estoque atualizada = movimentacoesRepository.save(movimentacao);
        return modelMapper.map(atualizada, Movimentacoes_EstoqueDTOUpdateResponse.class);
    }

    @Transactional
    public void apagarMovimentacao(Integer id) {
        Movimentacoes_Estoque movimentacao = movimentacoesRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Movimentação não encontrada"));

        movimentacao.setStatus(-1); // apagado lógico
        movimentacoesRepository.save(movimentacao);
    }


}
