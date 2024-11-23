package com.v1.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.v1.server.model.DadosSensiveis;
import com.v1.server.repository.DadosSensiveisRepository;

@Service
public class DadosSensiveisService {
    @Autowired
    DadosSensiveisRepository dadosSensiveisRepository;
    

    public List<DadosSensiveis> listarDadosSensiveiss(){
        return dadosSensiveisRepository.findAll();
    }

    public DadosSensiveis salvarDados(DadosSensiveis dadosSensiveis){
        return dadosSensiveisRepository.save(dadosSensiveis);
    }

    public DadosSensiveis buscarDadosSensiveisById(Long id){
        return dadosSensiveisRepository.findById(id).orElse(null);
    }

    public void deletarDadosSensiveisById(Long id){
        dadosSensiveisRepository.deleteById(id);
    }

}
