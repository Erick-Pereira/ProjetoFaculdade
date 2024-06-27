package com.senac.CondoConnect.dtos;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate; 

public record ComunicadoRecord(
    @NotBlank String tituloComunicado, 
    @NotBlank String descricaoComunicado,
    LocalDate data 
) {}
