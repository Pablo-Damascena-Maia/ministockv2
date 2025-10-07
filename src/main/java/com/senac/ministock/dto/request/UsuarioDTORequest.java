package com.senac.ministock.dto.request;

import com.senac.ministock.entity.Perfil;
import com.senac.ministock.entity.RoleName;

import java.util.List;

public class UsuarioDTORequest {

    private String nome;
    private String email;
    private String senha;
    private Perfil perfil; // ADMIN, USER, etc.
    private Integer status;
    private List<String> rolesList;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<String> getRolesList() {
        return rolesList;
    }

    public void setRolesList(List<String> rolesList) {
        this.rolesList = rolesList;
    }
}
