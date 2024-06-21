package com.senac.CondoConnect.dtos;

import java.time.LocalDate;
import java.util.Date;

public record ReservaRecord(String comentario, LocalDate data, UsuarioRecord usuario, String espaco) {

}
