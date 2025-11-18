package com.project.Work360.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioRequest (

        @NotBlank(message = "O nome é obrigatório.")
        String nome,

        @Email(message = "Email inválido.")
        @NotBlank(message = "O email é obrigatório.")
        String email,

        @NotBlank(message = "A senha é obrigatória.")
        @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres.")
        String senha

){
}
