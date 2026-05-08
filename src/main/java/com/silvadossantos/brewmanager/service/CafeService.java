package com.silvadossantos.brewmanager.service;

import com.silvadossantos.brewmanager.model.Cafe;
import com.silvadossantos.brewmanager.repository.CafeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CafeService {
    private final CafeRepository cafeRepository;

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
        cafeRepository.deleteById(id);
    }
}
