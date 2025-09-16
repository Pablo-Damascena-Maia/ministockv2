package com.senac.ministock.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ConfiguracaoDTORequest {

    @NotBlank
    private String configuracaoChave;

    @NotBlank
    private String configuracaoValor;

    private String configuracaoDescricao;

    @NotNull
    private Integer configuracaoStatus;
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getConfiguracaoChave() { return configuracaoChave; }
    public void setConfiguracaoChave(String configuracaoChave) { this.configuracaoChave = configuracaoChave; }

    public String getConfiguracaoValor() { return configuracaoValor; }
    public void setConfiguracaoValor(String configuracaoValor) { this.configuracaoValor = configuracaoValor; }

    public String getConfiguracaoDescricao() { return configuracaoDescricao; }
    public void setConfiguracaoDescricao(String configuracaoDescricao) { this.configuracaoDescricao = configuracaoDescricao; }

    public Integer getConfiguracaoStatus() { return configuracaoStatus; }
    public void setConfiguracaoStatus(Integer configuracaoStatus) { this.configuracaoStatus = configuracaoStatus; }
}
