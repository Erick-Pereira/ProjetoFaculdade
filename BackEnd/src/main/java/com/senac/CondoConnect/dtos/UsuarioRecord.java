package com.senac.CondoConnect.dtos;

import com.senac.CondoConnect.Enum.RoleName;

import jakarta.validation.constraints.NotBlank;

public record UsuarioRecord(String nomeUsuario, @NotBlank String apartamentoUsuario, String password, String username, RoleName role) {

}
