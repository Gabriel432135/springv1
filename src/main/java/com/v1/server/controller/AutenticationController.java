package com.v1.server.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.v1.server.model.DadosSensiveis;
import com.v1.server.model.Usuario;
import com.v1.server.model.DTOs.AuthenticationDTO;
import com.v1.server.model.DTOs.LoginResponseDTO;
import com.v1.server.model.DTOs.RegisterDTO;
import com.v1.server.service.RoleService;
import com.v1.server.service.TokenService;
import com.v1.server.service.UsuarioService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("auth")
public class AutenticationController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    RoleService roleService;

    @Autowired
    TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody  AuthenticationDTO authenticationDTO) {
        var emailPassword = new UsernamePasswordAuthenticationToken(authenticationDTO.email(), authenticationDTO.senha());
        var auth = authenticationManager.authenticate(emailPassword);
        var tokenDeUsuario = tokenService.generateToken((Usuario) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(tokenDeUsuario));
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterDTO> register(@RequestBody RegisterDTO registerDTO) {
        if(this.usuarioService.buscaruUsuarioByEmail(registerDTO.email())!=null) return ResponseEntity.badRequest().build();
        var encriptedPassword = bCryptPasswordEncoder.encode(registerDTO.senha());
        
        //---------------------

        var dadosSensiveis = new DadosSensiveis();
        dadosSensiveis.setSenhaHash(encriptedPassword);

        var usuario = new Usuario();

        usuario.setNome(registerDTO.nome());
        usuario.setEmail(registerDTO.email());
        usuario.setDadosSensiveis(dadosSensiveis);

        dadosSensiveis.setRoles(roleService.listarRolesPorNomes(List.of("ROLE_USER"))); //Quando ele acaba de se registrar, é apenas um USER.

        usuario = usuarioService.salvarUsuario(usuario);

        
        return new ResponseEntity<RegisterDTO>(registerDTO, HttpStatus.CREATED);
    }
    
    
    
}
