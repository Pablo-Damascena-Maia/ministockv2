package com.senac.ministock.dto.response;

import com.senac.ministock.entity.Role;

import java.util.List;

public class RecoveryUsuarioDto {
    private Long id;
    private String email;
    private List<Role> roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
