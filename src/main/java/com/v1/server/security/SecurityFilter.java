package com.v1.server.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.v1.server.repository.UsuarioRepository;
import com.v1.server.service.TokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter{

    @Autowired
    TokenService tokenService;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String token = this.recoverToken(request); //Recuperamos o token do campo Authorization no request
        if(token != null){
            //Pega o email caso a validação seja feita com sucesso
            String emailSubject = tokenService.validateToken(token);
            if(!emailSubject.isBlank()){
                UserDetails usuario = usuarioRepository.findByEmail(emailSubject);

                //Geração de um token de credenciais para o spring, contendo o usuario e as roles dele
                var authenticationToken = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response); //Vá para o próximo filtro, passando request e response para ele
    }
        
    private String recoverToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if(authHeader==null) return null;

        return authHeader.replace("Bearer ", "");
    }
    
}
