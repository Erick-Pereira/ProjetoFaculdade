package com.senac.CondoConnect.dtos;

import java.time.LocalDate;
import java.time.LocalTime;

import com.senac.CondoConnect.Model.UsuarioModel;

public record AssembleiaRecord(String titulo, String descricao, LocalDate data, UsuarioModel usuario, LocalTime hora) {

}
