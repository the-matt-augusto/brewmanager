package com.silvadossantos.brewmanager.service;

import com.silvadossantos.brewmanager.exception.EntityInUseException;
import com.silvadossantos.brewmanager.model.TipoInfusao;
import com.silvadossantos.brewmanager.repository.ReceitaRepository;
import com.silvadossantos.brewmanager.repository.TipoInfusaoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TipoInfusaoServiceTest {

    @Mock
    private TipoInfusaoRepository tipoInfusaoRepository;

    @Mock
    private ReceitaRepository receitaRepository;

    @InjectMocks
    private TipoInfusaoService tipoInfusaoService;

    @Test
    @DisplayName("Deve retornar uma lista de todos os tipos de infusão")
    void deveRetornarListaDeTodosOsTiposDeInfusao() {
        TipoInfusao type = TipoInfusao.builder().id(1L).nome("V60").build();
        when(tipoInfusaoRepository.findAll()).thenReturn(List.of(type));

        List<TipoInfusao> result = tipoInfusaoService.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getNome()).isEqualTo("V60");
        verify(tipoInfusaoRepository).findAll();
    }

    @Test
    @DisplayName("Deve salvar e retornar um tipo de infusão")
    void deveSalvarERetornarTipoDeInfusao() {
        TipoInfusao type = TipoInfusao.builder().nome("Aeropress").build();
        TipoInfusao savedType = TipoInfusao.builder().id(1L).nome("Aeropress").build();
        when(tipoInfusaoRepository.save(any(TipoInfusao.class))).thenReturn(savedType);

        TipoInfusao result = tipoInfusaoService.save(type);

        assertThat(result.getId()).isNotNull();
        assertThat(result.getNome()).isEqualTo("Aeropress");
        verify(tipoInfusaoRepository).save(type);
    }

    @Test
    @DisplayName("Deve encontrar tipo de infusão por id")
    void deveEncontrarTipoDeInfusaoPorId() {
        TipoInfusao type = TipoInfusao.builder().id(1L).nome("V60").build();
        when(tipoInfusaoRepository.findById(1L)).thenReturn(Optional.of(type));

        TipoInfusao result = tipoInfusaoService.findById(1L);

        assertThat(result.getNome()).isEqualTo("V60");
    }

    @Test
    @DisplayName("Deve lançar exceção quando o tipo de infusão não for encontrado")
    void deveLancarExcecaoQuandoTipoDeInfusaoNaoEncontrado() {
        when(tipoInfusaoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> tipoInfusaoService.findById(1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Tipo de infusão não encontrado: 1");
    }

    @Test
    @DisplayName("Deve excluir tipo de infusão por id quando não há receitas vinculadas")
    void deveExcluirTipoDeInfusaoPorId() {
        TipoInfusao tipo = TipoInfusao.builder().id(1L).nome("V60").build();
        when(tipoInfusaoRepository.findById(1L)).thenReturn(Optional.of(tipo));
        when(receitaRepository.existsByTipoInfusao(tipo)).thenReturn(false);
        doNothing().when(tipoInfusaoRepository).deleteById(1L);

        tipoInfusaoService.deleteById(1L);

        verify(tipoInfusaoRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Deve lançar EntityInUseException ao excluir tipo de infusão vinculado a receitas")
    void deveLancarEntityInUseExceptionAoExcluirTipoInfusaoVinculadoAReceitas() {
        TipoInfusao tipo = TipoInfusao.builder().id(1L).nome("V60").build();
        when(tipoInfusaoRepository.findById(1L)).thenReturn(Optional.of(tipo));
        when(receitaRepository.existsByTipoInfusao(tipo)).thenReturn(true);

        assertThatThrownBy(() -> tipoInfusaoService.deleteById(1L))
                .isInstanceOf(EntityInUseException.class)
                .hasMessageContaining("receitas");

        verify(tipoInfusaoRepository, never()).deleteById(any());
    }
}
