package com.silvadossantos.brewmanager.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "receitas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Receita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "infusao_type_id")
    private TipoInfusao tipoInfusao;

    @ManyToOne
    @JoinColumn(name = "cafe_id")
    private Cafe cafe;

    @OneToMany(mappedBy = "receita", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<IngredienteReceita> ingredientes = new ArrayList<>();

    @Column(name = "proporcao")
    private String proporcao;

    @Column(name = "tempo_infusao")
    private Integer tempoInfusao;

    @Column(name = "nota_sensorial")
    private Integer notaSensorial;
}
