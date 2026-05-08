package com.silvadossantos.brewmanager.repository;

import com.silvadossantos.brewmanager.model.TipoInfusao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoInfusaoRepository extends JpaRepository<TipoInfusao, Long> {
}
