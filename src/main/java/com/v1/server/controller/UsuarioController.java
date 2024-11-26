package com.v1.server.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.v1.server.model.DadosSensiveis;

import com.v1.server.model.Usuario;
import com.v1.server.model.DTOs.AddDataDTO;
import com.v1.server.model.DTOs.AddRoleDTO;
import com.v1.server.model.DTOs.UsuarioRequestDTO;
import com.v1.server.service.RoleService;
import com.v1.server.service.UsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RoleService roleService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping
    public List<Usuario> listarUsuarios(){
        return usuarioService.listarUsuarios();
    }
    
    @PostMapping
    public ResponseEntity<UsuarioRequestDTO> criarUsuario(@RequestBody @Validated UsuarioRequestDTO usuarioRequestDTO){
        if(usuarioRequestDTO.nome().isBlank() || usuarioRequestDTO.email().isBlank() || usuarioRequestDTO.senha().isBlank() || this.usuarioService.buscaruUsuarioByEmail(usuarioRequestDTO.email())!=null) return ResponseEntity.badRequest().build();
        
        var encriptedPassword = bCryptPasswordEncoder.encode(usuarioRequestDTO.senha());

        var dadosSensiveis = new DadosSensiveis();
        dadosSensiveis.setDado(usuarioRequestDTO.dado());
        dadosSensiveis.setSenhaHash(encriptedPassword);

        var usuario = new Usuario();

        usuario.setNome(usuarioRequestDTO.nome());
        usuario.setEmail(usuarioRequestDTO.email());
        usuario.setDadosSensiveis(dadosSensiveis);

        dadosSensiveis.setRoles(roleService.listarRolesPorNomes(usuarioRequestDTO.roles()));

        usuario = usuarioService.salvarUsuario(usuario);

        
        return new ResponseEntity<UsuarioRequestDTO>(usuarioRequestDTO, HttpStatus.CREATED);
    }

    @GetMapping("/busca")
    public ResponseEntity<Usuario> buscarUsuarioPorId(@Validated @RequestParam("id") Long id){
        Usuario usuario = usuarioService.buscarUsuarioById(id);
        return usuario != null ? ResponseEntity.ok(usuario):ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deletarUsuario(@Validated @RequestParam("id") Long id){
        if(usuarioService.buscarUsuarioById(id)==null) return ResponseEntity.badRequest().build();
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/permitir")
    public ResponseEntity<Void> adicionarRole(@RequestBody @Validated AddRoleDTO roleRequest){
        var usuario = usuarioService.buscarUsuarioById(roleRequest.id());
        var role = roleService.buscarRolePorNome(roleRequest.roleName());

        if(usuario==null || role==null) return ResponseEntity.badRequest().build();
        
        if(!usuario.getDadosSensiveis().getRoles().contains(role)){
            usuario.getDadosSensiveis().getRoles().add(role);
            usuarioService.salvarUsuario(usuario);
        }else{
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        return ResponseEntity.ok().build();

    }

    @PatchMapping("/revogar")
    public ResponseEntity<Void> revogarRole(@RequestBody @Validated AddRoleDTO roleRequest){
        var usuario = usuarioService.buscarUsuarioById(roleRequest.id());
        var role = roleService.buscarRolePorNome(roleRequest.roleName());

        if(usuario==null || role==null) return ResponseEntity.badRequest().build();
        
        if(usuario.getDadosSensiveis().getRoles().contains(role)){
            usuario.getDadosSensiveis().getRoles().remove(role);
            usuarioService.salvarUsuario(usuario);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok().build();

    }

    @PatchMapping("/dados")
    public ResponseEntity<Object> atualizarDados(@RequestBody @Validated AddDataDTO dataRequest){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var usuario = (Usuario) authentication.getPrincipal();
        if(usuario==null) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Erro de autenticação");

        usuario.getDadosSensiveis().setDado(dataRequest.dados());
        usuarioService.salvarUsuario(usuario);

        return ResponseEntity.ok().build();
    }
    
}
