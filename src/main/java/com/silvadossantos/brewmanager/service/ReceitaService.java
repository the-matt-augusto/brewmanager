package com.silvadossantos.brewmanager.service;

import com.silvadossantos.brewmanager.model.Receita;
import com.silvadossantos.brewmanager.repository.ReceitaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReceitaService {
    private final ReceitaRepository receitaRepository;

    public List<Receita> findAll() {
        return receitaRepository.findAll();
    }

    public Receita save(Receita receita) {
        validateNotaSensorial(receita.getNotaSensorial());
        validateRatio(receita.getRatio());
        return receitaRepository.save(receita);
    }

    public Receita findById(Long id) {
        return receitaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Receita não encontrada: " + id));
    }

    public void deleteById(Long id) {
        receitaRepository.deleteById(id);
    }

    private void validateNotaSensorial(Integer nota) {
        if (nota != null && (nota < 1 || nota > 5)) {
            throw new IllegalArgumentException("Nota sensorial deve ser entre 1 e 5.");
        }
    }

    private void validateRatio(String ratio) {
        if (ratio != null && !ratio.isBlank() && !ratio.matches("\\d+:\\d+")) {
            throw new IllegalArgumentException("Ratio inválido. Use o formato 1:15.");
        }
    }
}
