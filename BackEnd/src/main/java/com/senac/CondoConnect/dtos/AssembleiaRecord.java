package com.senac.CondoConnect.dtos;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;

import com.senac.CondoConnect.Model.UsuarioModel;

public record AssembleiaRecord(String titulo, String descricao, LocalDate data, UsuarioModel usuario, Time hora) {

}
