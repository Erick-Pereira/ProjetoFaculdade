package com.senac.CondoConnect.dtos;


import com.senac.CondoConnect.Model.UsuarioModel;

public record ComunicadoRecord(String tituloComunicado, String descricaoComunicado, UsuarioModel usuario) {

}
