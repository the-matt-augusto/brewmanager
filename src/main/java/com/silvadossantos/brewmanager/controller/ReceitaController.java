package com.silvadossantos.brewmanager.controller;

import com.silvadossantos.brewmanager.model.Receita;
import com.silvadossantos.brewmanager.service.CafeService;
import com.silvadossantos.brewmanager.service.ReceitaService;
import com.silvadossantos.brewmanager.service.TipoInfusaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
            @ModelAttribute Receita receita,
            Model model) {
        if (cafeId != null) receita.setCafe(cafeService.findById(cafeId));
        if (tipoInfusaoId != null) receita.setTipoInfusao(tipoInfusaoService.findById(tipoInfusaoId));
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
            @ModelAttribute Receita receita,
            Model model) {
        receita.setId(id);
        if (cafeId != null) receita.setCafe(cafeService.findById(cafeId));
        if (tipoInfusaoId != null) receita.setTipoInfusao(tipoInfusaoService.findById(tipoInfusaoId));
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
}
