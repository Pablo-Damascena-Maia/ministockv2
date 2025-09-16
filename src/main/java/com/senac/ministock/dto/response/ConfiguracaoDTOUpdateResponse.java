package com.senac.ministock.dto.response;

public class ConfiguracaoDTOUpdateResponse {
    private Integer configuracaoId;
    private Integer status;

    public ConfiguracaoDTOUpdateResponse() {}

    public ConfiguracaoDTOUpdateResponse(Integer configuracaoId, Integer status) {
        this.configuracaoId = configuracaoId;
        this.status = status;
    }

    public Integer getConfiguracaoId() {
        return configuracaoId;
    }

    public void setConfiguracaoId(Integer configuracaoId) {
        this.configuracaoId = configuracaoId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
