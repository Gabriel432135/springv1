package com.v1.server.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;

@Entity
public class DadosSensiveis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob
    private String dado;
    @Column(nullable = false)
    private String senhaHash;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name =  "usuario_role", //Nome da tabela de junção
        joinColumns = @JoinColumn(name = "usuario_id"), //Coluna do usuário
        inverseJoinColumns = @JoinColumn(name = "role_id") //Coluna do role
    )
    private List<Role> roles;

    @JsonIgnore
    @OneToOne(mappedBy = "dadosSensiveis")
    private Usuario usuario;

    
    //----------------------------------------------------

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles){
        this.roles = roles;
    }

    @JsonIgnore
    public ArrayList<? extends GrantedAuthority> getSpringRoles(){
        var SProleList = new ArrayList<SimpleGrantedAuthority>();

        for (var role : this.getRoles()) {
            switch (role.getRoleName()) {
                case "ROLE_ADMIN":
                    SProleList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                    break;
                
                case "ROLE_USER":
                    SProleList.add(new SimpleGrantedAuthority("ROLE_USER"));
                    break;
        
                case "ROLE_MODERATOR":
                    SProleList.add(new SimpleGrantedAuthority("ROLE_MODERATOR"));
                    break;
        
                case "ROLE_GUEST":
                    SProleList.add(new SimpleGrantedAuthority("ROLE_GUEST"));
                    break;
        
                // Adicione mais roles conforme necessário
                case "ROLE_SUPER_ADMIN":
                    SProleList.add(new SimpleGrantedAuthority("ROLE_SUPER_ADMIN"));
                    break;
        
                default:
                    break;
            }
        }

        return SProleList;
        
    }

    public Long getId() {
        return this.id;
    }

    public String getDado() {
        return this.dado;
    }

    public void setDado(String dado) {
        this.dado = dado;
    }

    public String getSenhaHash() {
        return this.senhaHash;
    }

    public void setSenhaHash(String senhaHash) {
        this.senhaHash = senhaHash;
    }

    public Usuario getUsuario() {
        return this.usuario;
    }
    
}
