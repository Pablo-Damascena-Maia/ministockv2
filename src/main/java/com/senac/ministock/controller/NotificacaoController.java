package com.senac.ministock.controller;

import com.senac.ministock.dto.request.NotificacaoDTORequest;
import com.senac.ministock.dto.response.NotificacaoDTOResponse;
import com.senac.ministock.dto.response.NotificacaoDTOUpdateResponse;
import com.senac.ministock.service.NotificacaoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/notificacao")
public class NotificacaoController {

    private final NotificacaoService notificacaoService;

    public NotificacaoController(NotificacaoService notificacaoService) {
        this.notificacaoService = notificacaoService;
    }

    @GetMapping("/listar")
    @Operation(summary = "Listar notificações", description = "Endpoint para listar todas as notificações ativas")
    public ResponseEntity<List<NotificacaoDTOResponse>> listarNotificacoes() {
        return ResponseEntity.ok(notificacaoService.listarNotificacoes());
    }

    @GetMapping("/listarPorNotificacaoId/{id}")
    @Operation(summary = "Listar notificação pelo id", description = "Endpoint para listar notificação por Id")
    public ResponseEntity<NotificacaoDTOResponse> listarPorNotificacaoId(@PathVariable("id") Integer id) {
        NotificacaoDTOResponse notificacao = notificacaoService.listarPorNotificacaoId(id);
        return (notificacao != null) ? ResponseEntity.ok(notificacao) : ResponseEntity.noContent().build();
    }

    @PostMapping("/criar")
    @Operation(summary = "Criar nova notificação", description = "Endpoint para criar um novo registro de notificação")
    public ResponseEntity<NotificacaoDTOResponse> criarNotificacao(
            @Valid @RequestBody NotificacaoDTORequest notificacao
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(notificacaoService.criarNotificacao(notificacao));
    }

    @PutMapping("/atualizar/{id}")
    @Operation(summary = "Atualizar todos os dados da notificação", description = "Endpoint para atualizar o registro de notificação")
    public ResponseEntity<NotificacaoDTOResponse> atualizarNotificacao(
            @PathVariable("id") Integer id,
            @Valid @RequestBody NotificacaoDTORequest notificacaoDTORequest
    ) {
        return ResponseEntity.ok(notificacaoService.atualizarNotificacao(id, notificacaoDTORequest));
    }

    @PatchMapping("/atualizarStatus/{id}")
    @Operation(summary = "Atualizar campo status da notificação", description = "Endpoint para atualizar apenas o status da notificação")
    public ResponseEntity<NotificacaoDTOUpdateResponse> atualizarStatusNotificacao(
            @PathVariable("id") Integer id,
            @Valid @RequestBody NotificacaoDTORequest notificacaoDTOUpdateRequest
    ) {
        return ResponseEntity.ok(notificacaoService.atualizarStatusNotificacao(id, notificacaoDTOUpdateRequest));
    }

    @PatchMapping("/marcarComoLida/{id}")
    @Operation(summary = "Marcar notificação como lida", description = "Endpoint para marcar notificação como lida")
    public ResponseEntity<Void> marcarComoLida(@PathVariable("id") Integer id) {
        notificacaoService.marcarComoLida(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/apagar/{id}")
    @Operation(summary = "Apagar registro da notificação", description = "Endpoint para exclusão lógica da notificação")
    public ResponseEntity<Void> apagarNotificacao(@PathVariable("id") Integer id) {
        notificacaoService.apagarNotificacao(id);
        return ResponseEntity.noContent().build();
    }
}
