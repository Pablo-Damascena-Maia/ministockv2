package com.senac.ministock.dto.request;


import com.senac.ministock.entity.TipoM;

import java.util.Date;

public class Movimentacoes_EstoqueDTORequest {
    private TipoM tipoM;
    private Integer quantidade;
    private Date dataMovimentacao;
    private String observacao;
    private Double precoCompra;
    private Double precoVenda;
    private Integer status;
    private Integer usuarioId;
    private Integer produtoId;

    public Movimentacoes_EstoqueDTORequest() {}

    public TipoM getTipoM() {
        return tipoM;
    }

    public void setTipoM(TipoM tipoM) {
        this.tipoM = tipoM;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Date getDataMovimentacao() {
        return dataMovimentacao;
    }

    public void setDataMovimentacao(Date dataMovimentacao) {
        this.dataMovimentacao = dataMovimentacao;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Double getPrecoCompra() {
        return precoCompra;
    }

    public void setPrecoCompra(Double precoCompra) {
        this.precoCompra = precoCompra;
    }

    public Double getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(Double precoVenda) {
        this.precoVenda = precoVenda;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Integer getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Integer produtoId) {
        this.produtoId = produtoId;
    }
}
