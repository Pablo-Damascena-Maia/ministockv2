package com.senac.ministock.service;

import com.senac.ministock.dto.request.ProdutoDTORequest;
import com.senac.ministock.dto.response.ProdutoDTOResponse;
import com.senac.ministock.dto.response.ProdutoDTOUpdateResponse;
import com.senac.ministock.entity.Produto;
import com.senac.ministock.entity.Usuario;
import com.senac.ministock.repository.ProdutoRepository;
import com.senac.ministock.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ProdutoService(ProdutoRepository produtoRepository, UsuarioRepository usuarioRepository, ModelMapper modelMapper) {
        this.produtoRepository = produtoRepository;
        this.usuarioRepository = usuarioRepository;
        this.modelMapper = modelMapper;
    }

    public List<ProdutoDTOResponse> listarProdutos() {
        return produtoRepository.listarProdutos()
                .stream()
                .map(p -> modelMapper.map(p, ProdutoDTOResponse.class))
                .toList();
    }

    public ProdutoDTOResponse listarPorId(Integer id) {
        Produto p = produtoRepository.obterProdutoPeloId(id);
        return (p != null) ? modelMapper.map(p, ProdutoDTOResponse.class) : null;
    }

    @Transactional
    public ProdutoDTOResponse criarProduto(ProdutoDTORequest dto) {
        Produto p = new Produto();
        p.setNome(dto.getNome());
        p.setDescricao(dto.getDescricao());
        p.setDataEntrada(dto.getDataEntrada());
        p.setQuantidadeEstoque(dto.getQuantidadeEstoque() != null ? dto.getQuantidadeEstoque() : 0);
        p.setCodigoBarras(dto.getCodigoBarras());
        p.setImagemUrl(dto.getImagemUrl());
        p.setAtivo(dto.getAtivo() != null ? dto.getAtivo() : true);
        p.setStatus(dto.getStatus() != null ? dto.getStatus() : 1);
        p.setDataCriacao(new Date());

        if (dto.getUsuarioId() != null) {
            Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuario não encontrado"));
            p.setUsuario(usuario);
            p.setCriadoPor(usuario.getId());
        }

        Produto salvo = produtoRepository.save(p);
        ProdutoDTOResponse response = new ProdutoDTOResponse();
        modelMapper.map(salvo, response);
        return response;
    }

    @Transactional
    public ProdutoDTOResponse atualizarProduto(Integer id, ProdutoDTORequest dto) {
        Produto p = produtoRepository.obterProdutoPeloId(id);
        if (p == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Produto não encontrado");

        p.setNome(dto.getNome());
        p.setDescricao(dto.getDescricao());
        p.setDataEntrada(dto.getDataEntrada());
        if (dto.getQuantidadeEstoque() != null) p.setQuantidadeEstoque(dto.getQuantidadeEstoque());
        p.setCodigoBarras(dto.getCodigoBarras());
        p.setImagemUrl(dto.getImagemUrl());
        if (dto.getAtivo() != null) p.setAtivo(dto.getAtivo());
        if (dto.getStatus() != null) p.setStatus(dto.getStatus());
        p.setDataAtualizacao(new Date());

        if (dto.getUsuarioId() != null) {
            Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuario não encontrado"));
            p.setUsuario(usuario);
        }

        Produto salvo = produtoRepository.save(p);
        return modelMapper.map(salvo, ProdutoDTOResponse.class);
    }

    @Transactional
    public ProdutoDTOUpdateResponse atualizarStatusProduto(Integer id, ProdutoDTORequest dto) {
        Produto p = produtoRepository.obterProdutoPeloId(id);
        if (p == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Produto não encontrado");

        if (dto.getStatus() != null) p.setStatus(dto.getStatus());
        p.setDataAtualizacao(new Date());

        Produto salvo = produtoRepository.save(p);
        return modelMapper.map(salvo, ProdutoDTOUpdateResponse.class);
    }

    public void apagarProduto(Integer id) {
        produtoRepository.apagadoLogicoProduto(id);
    }
}
