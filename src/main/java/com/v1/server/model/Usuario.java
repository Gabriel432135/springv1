package com.v1.server.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Usuario implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Chave primária
    private Long id;
    @Column(nullable = false)
    private String nome;
    @Column(unique = true, nullable = false)
    private String email;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "dados_sensiveis_id", referencedColumnName = "id")
    private DadosSensiveis dadosSensiveis;

    public Long getId() {
        return this.id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public DadosSensiveis getDadosSensiveis() {
        return this.dadosSensiveis;
    }

    public void setDadosSensiveis(DadosSensiveis dadosSensiveis) {
        this.dadosSensiveis = dadosSensiveis;
    }
    
    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.dadosSensiveis.getSpringRoles();
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return this.dadosSensiveis.getSenhaHash();
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return this.getNome();
    }
    
}