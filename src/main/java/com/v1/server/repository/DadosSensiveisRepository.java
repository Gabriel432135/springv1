package com.v1.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.v1.server.model.DadosSensiveis;

public interface DadosSensiveisRepository extends JpaRepository<DadosSensiveis, Long>{
    
}
