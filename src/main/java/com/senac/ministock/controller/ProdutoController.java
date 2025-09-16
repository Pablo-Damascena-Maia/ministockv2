package com.senac.ministock.controller;

import com.senac.ministock.dto.request.ProdutoDTORequest;
import com.senac.ministock.dto.response.ProdutoDTOResponse;
import com.senac.ministock.dto.response.ProdutoDTOUpdateResponse;
import com.senac.ministock.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/produto")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping("/listar")
    @Operation(summary = "Listar produtos", description = "Endpoint para listar todos os produtos")
    public ResponseEntity<List<ProdutoDTOResponse>> listarProdutos() {
        return ResponseEntity.ok(produtoService.listarProdutos());
    }

    @GetMapping("/listarPorId/{id}")
    @Operation(summary = "Listar produto por ID", description = "Endpoint para listar produto por ID")
    public ResponseEntity<ProdutoDTOResponse> listarPorId(@PathVariable("id") Integer id) {
        ProdutoDTOResponse dto = produtoService.listarPorId(id);
        if (dto == null) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/criar")
    @Operation(summary = "Criar produto", description = "Endpoint para criar um novo produto")
    public ResponseEntity<ProdutoDTOResponse> criarProduto(@Valid @RequestBody ProdutoDTORequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(produtoService.criarProduto(dto));
    }

    @PutMapping("/atualizar/{id}")
    @Operation(summary = "Atualizar produto", description = "Endpoint para atualizar todos os dados do produto")
    public ResponseEntity<ProdutoDTOResponse> atualizarProduto(
            @PathVariable("id") Integer id,
            @Valid @RequestBody ProdutoDTORequest dto) {
        return ResponseEntity.ok(produtoService.atualizarProduto(id, dto));
    }

    @PatchMapping("/atualizarStatus/{id}")
    @Operation(summary = "Atualizar status do produto", description = "Endpoint para atualizar apenas o status")
    public ResponseEntity<ProdutoDTOUpdateResponse> atualizarStatus(
            @PathVariable("id") Integer id,
            @Valid @RequestBody ProdutoDTORequest dto) {
        return ResponseEntity.ok(produtoService.atualizarStatusProduto(id, dto));
    }

    @DeleteMapping("/apagar/{id}")
    @Operation(summary = "Apagar produto", description = "Endpoint para apagar produto")
    public ResponseEntity<Void> apagar(@PathVariable("id") Integer id) {
        produtoService.apagarProduto(id);
        return ResponseEntity.noContent().build();
    }
}
