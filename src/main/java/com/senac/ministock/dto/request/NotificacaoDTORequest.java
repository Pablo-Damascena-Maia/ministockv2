package com.senac.ministock.dto.request;

import com.senac.ministock.entity.TipoN;


public class NotificacaoDTORequest {

    private String titulo;
    private String mensagem;
    private TipoN tipoN;
    private Integer  lida;
    private Integer usuarioId;
    private Integer status;

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

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
