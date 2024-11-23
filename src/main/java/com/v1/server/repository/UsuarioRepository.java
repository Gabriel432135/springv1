package com.v1.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.v1.server.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    Usuario findByEmail(String email);
    UserDetails findByNome(String nome); //O nome do método "by NOME" importa, o que vem depois de findBy precisa ser o nome real da variável
}
