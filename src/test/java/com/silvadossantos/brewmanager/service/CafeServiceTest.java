package com.silvadossantos.brewmanager.service;

import com.silvadossantos.brewmanager.model.Cafe;
import com.silvadossantos.brewmanager.repository.CafeRepository;
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
class CafeServiceTest {

    @Mock
    private CafeRepository cafeRepository;

    @InjectMocks
    private CafeService cafeService;

    @Test
    @DisplayName("Deve retornar uma lista de todos os cafés")
    void deveRetornarListaDeTodosOsCafes() {
        Cafe cafe = Cafe.builder().id(1L).nome("Test Coffee").build();
        when(cafeRepository.findAll()).thenReturn(List.of(cafe));

        List<Cafe> result = cafeService.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getNome()).isEqualTo("Test Coffee");
        verify(cafeRepository).findAll();
    }

    @Test
    @DisplayName("Deve salvar e retornar um café")
    void deveSalvarERetornarCafe() {
        Cafe cafe = Cafe.builder().nome("New Coffee").build();
        Cafe savedCafe = Cafe.builder().id(1L).nome("New Coffee").build();
        when(cafeRepository.save(any(Cafe.class))).thenReturn(savedCafe);

        Cafe result = cafeService.save(cafe);

        assertThat(result.getId()).isNotNull();
        assertThat(result.getNome()).isEqualTo("New Coffee");
        verify(cafeRepository).save(cafe);
    }

    @Test
    @DisplayName("Deve encontrar café por id")
    void deveEncontrarCafePorId() {
        Cafe cafe = Cafe.builder().id(1L).nome("Found Coffee").build();
        when(cafeRepository.findById(1L)).thenReturn(Optional.of(cafe));

        Cafe result = cafeService.findById(1L);

        assertThat(result.getNome()).isEqualTo("Found Coffee");
    }

    @Test
    @DisplayName("Deve lançar exceção quando o café não for encontrado")
    void deveLancarExcecaoQuandoCafeNaoEncontrado() {
        when(cafeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> cafeService.findById(1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Café não encontrado: 1");
    }

    @Test
    @DisplayName("Deve excluir café por id")
    void deveExcluirCafePorId() {
        doNothing().when(cafeRepository).deleteById(1L);

        cafeService.deleteById(1L);

        verify(cafeRepository).deleteById(1L);
    }
}
