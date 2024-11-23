package com.v1.server.model.DTOs;

import java.util.List;

public record UsuarioRequestDTO
(
    String nome,
    String email,
    String dado,
    String senha,
    List<String> roles
){}
