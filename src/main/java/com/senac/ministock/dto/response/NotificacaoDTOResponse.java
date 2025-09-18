package com.senac.ministock.dto.response;

import com.senac.ministock.repository.entity.TipoN;

import java.util.Date;

public class NotificacaoDTOResponse {

    private Integer id;
    private String titulo;
    private String mensagem;
    private TipoN tipoN;
    private Integer lida;
    private Date dataCriacao;
    private Integer status;
    private Integer usuarioId;


    public NotificacaoDTOResponse() {}


    public NotificacaoDTOResponse(Integer id, String titulo, String mensagem, TipoN tipoN,
                                  Integer lida, Date dataCriacao, Integer status, Integer usuarioId) {
        this.id = id;
        this.titulo = titulo;
        this.mensagem = mensagem;
        this.tipoN = tipoN;
        this.lida = lida;
        this.dataCriacao = dataCriacao;
        this.status = status;
        this.usuarioId = usuarioId;
    }

    public NotificacaoDTOResponse(Integer id, String titulo, String mensagem, TipoN tipoN, Integer lida, Date dataCriacao, Integer status, int id1) {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public TipoN getTipoN() {
        return tipoN;
    }

    public void setTipoN(TipoN tipoN) {
        this.tipoN = tipoN;
    }

    public Integer getLida() {
        return lida;
    }

    public void setLida(Integer lida) {
        this.lida = lida;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
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
}