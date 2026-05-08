package com.silvadossantos.brewmanager;

import com.silvadossantos.brewmanager.repository.TipoInfusaoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DataInitializerTest {

    @Mock
    private TipoInfusaoRepository tipoInfusaoRepository;

    @InjectMocks
    private DataInitializer dataInitializer;

    @Test
    @DisplayName("Deve popular o banco com 5 tipos de infusão quando estiver vazio")
    void devePopularQuandoVazio() throws Exception {
        when(tipoInfusaoRepository.count()).thenReturn(0L);

        dataInitializer.run();

        verify(tipoInfusaoRepository).saveAll(anyList());
    }

    @Test
    @DisplayName("Não deve popular o banco quando já estiver povoado")
    void deveNaoPopularQuandoJaPovoado() throws Exception {
        when(tipoInfusaoRepository.count()).thenReturn(5L);

        dataInitializer.run();

        verify(tipoInfusaoRepository, never()).saveAll(anyList());
    }
}
