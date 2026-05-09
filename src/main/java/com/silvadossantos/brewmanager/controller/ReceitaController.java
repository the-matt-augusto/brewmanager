package com.silvadossantos.brewmanager.controller;

import com.silvadossantos.brewmanager.dto.ReceitaDetalheDTO;
import com.silvadossantos.brewmanager.model.Cafe;
import com.silvadossantos.brewmanager.model.IngredienteReceita;
import com.silvadossantos.brewmanager.model.Receita;
import com.silvadossantos.brewmanager.service.CafeService;
import com.silvadossantos.brewmanager.service.ReceitaService;
import com.silvadossantos.brewmanager.service.TipoInfusaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/receitas")
@RequiredArgsConstructor
public class ReceitaController {

    private final ReceitaService receitaService;
    private final CafeService cafeService;
    private final TipoInfusaoService tipoInfusaoService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("receitas", receitaService.findAll());
        return "lista-receitas";
    }

    @GetMapping("/novo")
    public String novoForm(Model model) {
        model.addAttribute("receita", new Receita());
        model.addAttribute("cafes", cafeService.findAll());
        model.addAttribute("tipos", tipoInfusaoService.findAll());
        return "form-receita";
    }

    @PostMapping
    public String salvar(
            @RequestParam(required = false) Long cafeId,
            @RequestParam(required = false) Long tipoInfusaoId,
            @RequestParam(required = false) String observacoes,
            @ModelAttribute Receita receita,
            Model model) {
        if (cafeId != null) receita.setCafe(cafeService.findById(cafeId));
        if (tipoInfusaoId != null) receita.setTipoInfusao(tipoInfusaoService.findById(tipoInfusaoId));
        if (observacoes != null) receita.setObservacoes(observacoes);

        // Associar ingredientes à receita (mapear referência bidirecional)
        if (receita.getIngredientes() != null) {
            for (IngredienteReceita ing : receita.getIngredientes()) {
                ing.setReceita(receita);
            }
        }

        try {
            receitaService.save(receita);
            return "redirect:/receitas";
        } catch (IllegalArgumentException e) {
            model.addAttribute("erro", e.getMessage());
            model.addAttribute("receita", receita);
            model.addAttribute("cafes", cafeService.findAll());
            model.addAttribute("tipos", tipoInfusaoService.findAll());
            return "form-receita";
        }
    }

    @GetMapping("/{id}/editar")
    public String editarForm(@PathVariable Long id, Model model) {
        model.addAttribute("receita", receitaService.findById(id));
        model.addAttribute("cafes", cafeService.findAll());
        model.addAttribute("tipos", tipoInfusaoService.findAll());
        return "form-receita";
    }

    @PostMapping("/{id}")
    public String atualizar(
            @PathVariable Long id,
            @RequestParam(required = false) Long cafeId,
            @RequestParam(required = false) Long tipoInfusaoId,
            @RequestParam(required = false) String observacoes,
            @ModelAttribute Receita receita,
            Model model) {
        receita.setId(id);
        if (cafeId != null) receita.setCafe(cafeService.findById(cafeId));
        if (tipoInfusaoId != null) receita.setTipoInfusao(tipoInfusaoService.findById(tipoInfusaoId));
        if (observacoes != null) receita.setObservacoes(observacoes);

        // Associar ingredientes à receita (mapear referência bidirecional)
        if (receita.getIngredientes() != null) {
            for (IngredienteReceita ing : receita.getIngredientes()) {
                ing.setReceita(receita);
            }
        }

        try {
            receitaService.save(receita);
            return "redirect:/receitas";
        } catch (IllegalArgumentException e) {
            model.addAttribute("erro", e.getMessage());
            model.addAttribute("receita", receita);
            model.addAttribute("cafes", cafeService.findAll());
            model.addAttribute("tipos", tipoInfusaoService.findAll());
            return "form-receita";
        }
    }

    @GetMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id) {
        receitaService.deleteById(id);
        return "redirect:/receitas";
    }

    @GetMapping("/{id}/detalhes")
    @ResponseBody
    public ResponseEntity<ReceitaDetalheDTO> detalhes(@PathVariable Long id) {
        Receita r = receitaService.findById(id);
        Cafe cafe = r.getCafe();
        List<ReceitaDetalheDTO.IngredienteDTO> ings = r.getIngredientes() == null ? List.of() :
            r.getIngredientes().stream()
                .map(i -> ReceitaDetalheDTO.IngredienteDTO.builder()
                    .nome(i.getNome())
                    .quantidade(i.getQuantidade())
                    .build())
                .collect(Collectors.toList());
        ReceitaDetalheDTO dto = ReceitaDetalheDTO.builder()
            .id(r.getId())
            .cafeName(cafe != null ? cafe.getNome() : null)
            .cafeMarca(cafe != null ? cafe.getMarcaTorrefacao() : null)
            .cafeOrigem(cafe != null ? cafe.getOrigem() : null)
            .cafeTorra(cafe != null ? cafe.getNivelTorra() : null)
            .cafeTipo(cafe != null ? cafe.getTipoCafe() : null)
            .cafeMoagem(cafe != null ? cafe.getNivelMoagem() : null)
            .tipoInfusao(r.getTipoInfusao() != null ? r.getTipoInfusao().getNome() : null)
            .proporcao(r.getProporcao())
            .tempoInfusao(r.getTempoInfusao())
            .notaSensorial(r.getNotaSensorial())
            .observacoes(r.getObservacoes())
            .ingredientes(ings)
            .build();
        return ResponseEntity.ok(dto);
    }
}
