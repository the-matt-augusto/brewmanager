package com.silvadossantos.brewmanager.repository;

import com.silvadossantos.brewmanager.model.Cafe;
import com.silvadossantos.brewmanager.model.Receita;
import com.silvadossantos.brewmanager.model.TipoInfusao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceitaRepository extends JpaRepository<Receita, Long> {
    boolean existsByCafe(Cafe cafe);
    boolean existsByTipoInfusao(TipoInfusao tipoInfusao);
}
