package com.senac.ministock.dto.response;

public class ConfiguracaoDTOResponse {
    private Integer configuracaoId;
    private String configuracaoChave;
    private String configuracaoValor;
    private String configuracaoDescricao;
    private Integer status;

    public Integer getConfiguracaoId() {
        return configuracaoId;
    }

    public void setConfiguracaoId(Integer configuracaoId) {
        this.configuracaoId = configuracaoId;
    }

    public String getConfiguracaoChave() {
        return configuracaoChave;
    }

    public void setConfiguracaoChave(String configuracaoChave) {
        this.configuracaoChave = configuracaoChave;
    }

    public String getConfiguracaoValor() {
        return configuracaoValor;
    }

    public void setConfiguracaoValor(String configuracaoValor) {
        this.configuracaoValor = configuracaoValor;
    }

    public String getConfiguracaoDescricao() {
        return configuracaoDescricao;
    }

    public void setConfiguracaoDescricao(String configuracaoDescricao) {
        this.configuracaoDescricao = configuracaoDescricao;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
