package com.silvadossantos.brewmanager.model;

import jakarta.persistence.*;
import lombok.*;

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

    private String ratio;

    @Column(name = "tempo_infusao")
    private Integer tempoInfusao;

    @Column(name = "nota_sensorial")
    private Integer notaSensorial;
}
