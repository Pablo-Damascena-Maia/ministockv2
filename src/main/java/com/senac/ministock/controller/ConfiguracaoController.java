package com.senac.ministock.controller;

import com.senac.ministock.dto.request.ConfiguracaoDTORequest;
import com.senac.ministock.dto.response.ConfiguracaoDTOResponse;
import com.senac.ministock.dto.response.ConfiguracaoDTOUpdateResponse;
import com.senac.ministock.service.ConfiguracaoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/configuracao")
public class ConfiguracaoController {

    private final ConfiguracaoService configuracaoService;

    public ConfiguracaoController(ConfiguracaoService configuracaoService) {
        this.configuracaoService = configuracaoService;
    }

    @GetMapping("/listar")
    @Operation(summary = "Listar configurações", description = "Endpoint para listar todas as configurações")
    public ResponseEntity<List<ConfiguracaoDTOResponse>> listarConfiguracoes() {
        return ResponseEntity.ok(configuracaoService.listarConfiguracoes());
    }

    @GetMapping("/listarPorConfiguracaoId/{configuracaoId}")
    @Operation(summary = "Listar configuração pelo id", description = "Endpoint para listar configuração por Id")
    public ResponseEntity<ConfiguracaoDTOResponse> listarPorConfiguracaoId(
            @PathVariable Integer configuracaoId
    ) {
        ConfiguracaoDTOResponse c = configuracaoService.listarPorConfiguracaoId(configuracaoId);
        if (c == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(c);
    }

    @PostMapping("/criar")
    @Operation(summary = "Criar nova configuração", description = "Endpoint para criar um novo registro de configuração")
    public ResponseEntity<ConfiguracaoDTOResponse> criarConfiguracao(
            @Valid @RequestBody ConfiguracaoDTORequest dto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(configuracaoService.criarConfiguracao(dto));
    }

    @PutMapping("/atualizar/{configuracaoId}")
    @Operation(summary = "Atualizar todos os dados da configuração", description = "Endpoint para atualizar o registro de configuração")
    public ResponseEntity<ConfiguracaoDTOResponse> atualizarConfiguracao(
            @PathVariable Integer configuracaoId,
            @Valid @RequestBody ConfiguracaoDTORequest dto
    ) {
        return ResponseEntity.ok(configuracaoService.atualizarConfiguracao(configuracaoId, dto));
    }

    @PatchMapping("/atualizarStatus/{configuracaoId}")
    @Operation(summary = "Atualizar campo status da configuração", description = "Endpoint para atualizar apenas o status da configuração")
    public ResponseEntity<ConfiguracaoDTOUpdateResponse> atualizarStatusConfiguracao(
            @PathVariable Integer configuracaoId,
            @Valid @RequestBody ConfiguracaoDTORequest dto
    ) {
        return ResponseEntity.ok(configuracaoService.atualizarStatusConfiguracao(configuracaoId, dto));
    }

    @DeleteMapping("/apagar/{configuracaoId}")
    @Operation(summary = "Apagar registro da configuração", description = "Endpoint para apagar registro da configuração")
    public ResponseEntity<Void> apagarConfiguracao(@PathVariable Integer configuracaoId) {
        configuracaoService.apagarConfiguracao(configuracaoId);
        return ResponseEntity.noContent().build();
    }
}
