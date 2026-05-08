package com.silvadossantos.brewmanager;

import com.silvadossantos.brewmanager.model.TipoInfusao;
import com.silvadossantos.brewmanager.repository.TipoInfusaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final TipoInfusaoRepository tipoInfusaoRepository;

    @Override
    public void run(String... args) {
        if (tipoInfusaoRepository.count() == 0) {
            tipoInfusaoRepository.saveAll(List.of(
                TipoInfusao.builder().nome("V60").descricao("Coagem por gotejamento em cone de 60 graus").build(),
                TipoInfusao.builder().nome("Aeropress").descricao("Extração por pressão de ar, rápida e versátil").build(),
                TipoInfusao.builder().nome("French Press").descricao("Imersão total com êmbolo pressionador").build(),
                TipoInfusao.builder().nome("Chemex").descricao("Coagem com filtro grosso, bebida limpa e brilhante").build(),
                TipoInfusao.builder().nome("Moka").descricao("Cafeteira italiana, extração por pressão de vapor").build()
            ));
        }
    }
}
