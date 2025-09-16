package com.senac.ministock.entity;

import jakarta.persistence.*;
import org.w3c.dom.Text;

@Entity
@Table(name = "configuracao")
public class Configuracao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "configuracao_id")
    private Integer configuracaoId;

    @Column(name = "configuracao_chave", nullable = false)
    private String configuracaoChave;


    @Column(name = "configuracao_valor", nullable = false, unique = true)
    private String configuracaoValor;

    @Column(name = "configuracao_descricao")
    private String configuracaoDescricao;

    @Column(name = "configuracao_status")
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
