package com.silvadossantos.brewmanager.service;

import com.silvadossantos.brewmanager.exception.EntityInUseException;
import com.silvadossantos.brewmanager.model.TipoInfusao;
import com.silvadossantos.brewmanager.repository.ReceitaRepository;
import com.silvadossantos.brewmanager.repository.TipoInfusaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TipoInfusaoService {
    private final TipoInfusaoRepository tipoInfusaoRepository;
    private final ReceitaRepository receitaRepository;

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
        TipoInfusao tipo = findById(id);
        if (receitaRepository.existsByTipoInfusao(tipo)) {
            throw new EntityInUseException("Não é possível excluir: este método de infusão está vinculado a uma ou mais receitas.");
        }
        tipoInfusaoRepository.deleteById(id);
    }
}
