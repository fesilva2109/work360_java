package com.project.Work360.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.Work360.dto.AuthResponse;
import com.project.Work360.dto.LoginRequest;
import com.project.Work360.dto.UsuarioResponse;
import com.project.Work360.model.Usuario;
import com.project.Work360.security.TokenService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/login")
@Tag(name = "2 - Login", description = "Autenticação e geração de token JWT")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Operation(summary = "Realiza login e retorna um token JWT e os dados do usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso!",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthResponse.class))), // <-- Updated Schema
            @ApiResponse(responseCode = "400", description = "Credenciais inválidas!", content = @Content),
            @ApiResponse(responseCode = "403", description = "Acesso negado!", content = @Content)
    })
    @PostMapping
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginRequest request) { // <-- Updated return type

        var usernamePassword = new UsernamePasswordAuthenticationToken(
                request.email(),
                request.senha()
        );

        var auth = authenticationManager.authenticate(usernamePassword);

        // 1. Get the full User object from the authentication principal
        var usuario = (Usuario) auth.getPrincipal();

        // 2. Generate the token for this user
        var token = tokenService.generateToken(usuario);

        // 3. Create a safe response DTO for the user (without the password)
        var usuarioResponse = new UsuarioResponse(usuario.getId(), usuario.getNome(), usuario.getEmail());

        // 4. Build the final response containing the token and the user DTO
        var authResponse = new AuthResponse(token, usuarioResponse);

        // 5. Return the complete response
        return ResponseEntity.ok(authResponse);
    }
}
