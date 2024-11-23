package com.v1.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.v1.server.model.Usuario;
import com.v1.server.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> listarUsuarios(){
        return usuarioRepository.findAll();
    }

    public Usuario salvarUsuario(Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    public Usuario buscarUsuarioById(Long id){
        return usuarioRepository.findById(id).orElse(null);
    }

    public Usuario buscaruUsuarioByEmail(String email){
        return usuarioRepository.findByEmail(email);
    }

    public void deletarUsuario(Long id){
        usuarioRepository.deleteById(id);
    }
}
