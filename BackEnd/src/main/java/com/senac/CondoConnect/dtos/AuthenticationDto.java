package com.senac.CondoConnect.dtos;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationDto(@NotBlank String username, @NotBlank String password) {

}
