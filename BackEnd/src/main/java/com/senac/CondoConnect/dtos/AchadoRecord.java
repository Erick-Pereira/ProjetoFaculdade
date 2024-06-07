package com.senac.CondoConnect.dtos;

import com.senac.CondoConnect.Model.UsuarioModel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record AchadoRecord(@NotBlank String descricaoAchado, @NotBlank String tituloAchado, @NotNull LocalDate data, UsuarioModel usuario) {

}
