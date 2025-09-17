package com.senac.ministock.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name ="notificacao")
public class Notificacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="notificacao_id")
    private Integer id;

    @Column(name="notificacao_titulo", nullable = false)
    private String titulo;

    @Column(name="notificacao_mensagem", nullable = false, columnDefinition = "TEXT")
    private String mensagem;

    @Enumerated(EnumType.STRING)
    @Column(name="notificacao_tipo", nullable = false)
    private Tipo tipo = Tipo.INFORMATIVO; // default igual ao banco

    @Column(name="notificacao_lida")
    private Integer lida;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="notificacao_data_criacao", updatable = false, insertable = false)
    private Date dataCriacao; // será preenchido automaticamente no banco

    @Column(name="notificacao_status")
    private Integer status = 1; // ativo por padrão

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

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

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public Integer getLida() {
        return lida;
    }

    public void setLida(Integer lida) {
        this.lida = lida;
    }

    public void setLida(int lida) {
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
