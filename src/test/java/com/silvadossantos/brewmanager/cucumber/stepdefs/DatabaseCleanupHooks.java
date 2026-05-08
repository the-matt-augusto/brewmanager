package com.silvadossantos.brewmanager.cucumber.stepdefs;

import com.silvadossantos.brewmanager.repository.CafeRepository;
import com.silvadossantos.brewmanager.repository.ReceitaRepository;
import com.silvadossantos.brewmanager.repository.TipoInfusaoRepository;
import io.cucumber.java.After;
import org.springframework.beans.factory.annotation.Autowired;

public class DatabaseCleanupHooks {

    @Autowired
    private ReceitaRepository receitaRepository;

    @Autowired
    private CafeRepository cafeRepository;

    @Autowired
    private TipoInfusaoRepository tipoInfusaoRepository;

    @After
    public void cleanDatabase() {
        receitaRepository.deleteAllInBatch();
        cafeRepository.deleteAllInBatch();
        tipoInfusaoRepository.deleteAllInBatch();
    }
}
