package com.silvadossantos.brewmanager.service;

import com.silvadossantos.brewmanager.model.TipoInfusao;
import com.silvadossantos.brewmanager.repository.TipoInfusaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TipoInfusaoService {
    private final TipoInfusaoRepository tipoInfusaoRepository;

    public List<TipoInfusao> findAll() {
        return tipoInfusaoRepository.findAll();
    }

    public TipoInfusao save(TipoInfusao tipoInfusao) {
        return tipoInfusaoRepository.save(tipoInfusao);
    }

    public TipoInfusao findById(Long id) {
        return tipoInfusaoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tipo de infusão não encontrado: " + id));
    }

    public void deleteById(Long id) {
        tipoInfusaoRepository.deleteById(id);
    }
}
