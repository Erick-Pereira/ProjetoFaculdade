package com.senac.CondoConnect.dtos;

import java.sql.Date;
import java.time.LocalDate;

import com.senac.CondoConnect.Model.UsuarioModel;

import jakarta.validation.constraints.NotBlank;

public record AchadoRecord(@NotBlank String descricaoAchado, @NotBlank String tituloAchado, UsuarioModel usuario, LocalDate data) {

}
