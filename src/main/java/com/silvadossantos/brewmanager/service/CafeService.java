package com.silvadossantos.brewmanager.service;

import com.silvadossantos.brewmanager.exception.EntityInUseException;
import com.silvadossantos.brewmanager.model.Cafe;
import com.silvadossantos.brewmanager.repository.CafeRepository;
import com.silvadossantos.brewmanager.repository.ReceitaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CafeService {
    private final CafeRepository cafeRepository;
    private final ReceitaRepository receitaRepository;

    public List<Cafe> findAll() {
        return cafeRepository.findAll();
    }

    public Cafe save(Cafe cafe) {
        return cafeRepository.save(cafe);
    }

    public Cafe findById(Long id) {
        return cafeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Café não encontrado: " + id));
    }

    public void deleteById(Long id) {
        Cafe cafe = findById(id);
        if (receitaRepository.existsByCafe(cafe)) {
            throw new EntityInUseException("Não é possível excluir: este café está vinculado a uma ou mais receitas.");
        }
        cafeRepository.deleteById(id);
    }
}
