package com.senac.ministock.service;

import com.senac.ministock.dto.request.ConfiguracaoDTORequest;
import com.senac.ministock.dto.response.ConfiguracaoDTOResponse;
import com.senac.ministock.dto.response.ConfiguracaoDTOUpdateResponse;
import com.senac.ministock.entity.Configuracao;
import com.senac.ministock.repository.ConfiguracaoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConfiguracaoService {

    private final ConfiguracaoRepository configuracaoRepository;

    public ConfiguracaoService(ConfiguracaoRepository configuracaoRepository) {
        this.configuracaoRepository = configuracaoRepository;
    }

    public List<ConfiguracaoDTOResponse> listarConfiguracoes() {
        return configuracaoRepository.listarConfiguracoes()
                .stream()
                .map(this::converterParaDTOResponse)
                .collect(Collectors.toList());
    }

    public ConfiguracaoDTOResponse listarPorConfiguracaoId(Integer configuracaoId) {
        Configuracao configuracao = configuracaoRepository.obterConfiguracaoPeloId(configuracaoId);
        if (configuracao == null) return null;
        return converterParaDTOResponse(configuracao);
    }

    @Transactional
    public ConfiguracaoDTOResponse criarConfiguracao(ConfiguracaoDTORequest dto) {
        Configuracao configuracao = new Configuracao();
        configuracao.setConfiguracaoChave(dto.getConfiguracaoChave());
        configuracao.setConfiguracaoValor(dto.getConfiguracaoValor());
        configuracao.setStatus(dto.getStatus() != null ? dto.getStatus() : 1);
        configuracaoRepository.save(configuracao);
        return converterParaDTOResponse(configuracao);
    }

    @Transactional
    public ConfiguracaoDTOResponse atualizarConfiguracao(Integer configuracaoId, ConfiguracaoDTORequest dto) {
        Configuracao configuracaoExistente = configuracaoRepository.obterConfiguracaoPeloId(configuracaoId);
        if (configuracaoExistente == null) return null;

        configuracaoExistente.setConfiguracaoChave(
                dto.getConfiguracaoChave() != null ? dto.getConfiguracaoChave() : configuracaoExistente.getConfiguracaoChave()
        );
        configuracaoExistente.setConfiguracaoValor(
                dto.getConfiguracaoValor() != null ? dto.getConfiguracaoValor() : configuracaoExistente.getConfiguracaoValor()
        );
        configuracaoExistente.setStatus(dto.getStatus() != null ? dto.getStatus() : configuracaoExistente.getStatus());

        configuracaoRepository.save(configuracaoExistente);
        return converterParaDTOResponse(configuracaoExistente);
    }

    @Transactional
    public ConfiguracaoDTOUpdateResponse atualizarStatusConfiguracao(Integer configuracaoId, ConfiguracaoDTORequest dto) {
        Configuracao configuracaoExistente = configuracaoRepository.obterConfiguracaoPeloId(configuracaoId);
        if (configuracaoExistente == null) return null;

        configuracaoExistente.setStatus(dto.getStatus());
        configuracaoRepository.save(configuracaoExistente);
        return new ConfiguracaoDTOUpdateResponse(configuracaoExistente.getConfiguracaoId(), configuracaoExistente.getStatus());
    }

    @Transactional
    public void apagarConfiguracao(Integer configuracaoId) {
        configuracaoRepository.apagadoLogicoConfiguracao(configuracaoId);
    }

    private ConfiguracaoDTOResponse converterParaDTOResponse(Configuracao configuracao) {
        ConfiguracaoDTOResponse dto = new ConfiguracaoDTOResponse();
        dto.setConfiguracaoId(configuracao.getConfiguracaoId());
        dto.setConfiguracaoChave(configuracao.getConfiguracaoChave());
        dto.setConfiguracaoValor(configuracao.getConfiguracaoValor());
        dto.setStatus(configuracao.getStatus());
        return dto;
    }
}
