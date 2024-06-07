package com.senac.CondoConnect.dtos;

import java.time.LocalDate;

import com.senac.CondoConnect.Model.UsuarioModel;

import jakarta.validation.constraints.NotBlank;

public record ReservaRecord(
    @NotBlank String comentario, 
    LocalDate data, 
    @NotBlank String espaco,
    UsuarioModel usuario
) {}
