package com.silvadossantos.brewmanager.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ingredientesReceita")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IngredienteReceita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "receita_id", nullable = false)
    private Receita receita;

    @Column(nullable = false)
    private String nome;        // nome do ingrediente (ex: "Açúcar")

    @Column(nullable = false)
    private String quantidade;   // quantidade (ex: "2 colheres")
}
