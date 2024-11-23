package com.v1.server.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.v1.server.model.Usuario;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(Usuario usuario){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create().withIssuer("springv1-auth-api").withSubject(usuario.getEmail()).withExpiresAt(generateExpirationDate()).sign(algorithm);
            return token;
        }catch(JWTCreationException exception){
            throw new RuntimeException("Erro ao criar token", exception);
        }
        
    }

    public String validateToken(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            //Retorna o email do usuario autenticado
            return JWT.require(algorithm).withIssuer("springv1-auth-api").build().verify(token).getSubject();

        }catch(JWTVerificationException exception){
            return ""; //Não retornou nada porque a verificação falhou, seja porque expirou, o token é inválido, etc...
        }
    }

    private Instant generateExpirationDate(){
        //2 horas a partir de agora no horário de brasília
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
