package com.v1.server.model.DTOs;

public record RegisterDTO(
    String nome,
    String email,
    String senha
) {}
