package com.v1.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.v1.server.repository.UsuarioRepository;

@Service
public class AuthorizationService implements UserDetailsService{
    
    @Autowired
    UsuarioRepository usuarioRepository;
    /*
     * Isso será o método que o authenticationManager irá usar para autenticar o usuário
     * Apesar de se chamar loadUserByUsername, eu vou usar o email ao invés do nome
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return usuarioRepository.findByEmail(email);
    }
    
}
