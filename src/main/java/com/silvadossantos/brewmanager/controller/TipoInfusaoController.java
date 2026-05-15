package com.silvadossantos.brewmanager.controller;

import com.silvadossantos.brewmanager.exception.EntityInUseException;
import com.silvadossantos.brewmanager.model.TipoInfusao;
import com.silvadossantos.brewmanager.service.TipoInfusaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/tipos-infusao")
@RequiredArgsConstructor
public class TipoInfusaoController {

    private final TipoInfusaoService tipoInfusaoService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("tipos", tipoInfusaoService.findAll());
        return "lista-tipos-infusao";
    }

    @GetMapping("/novo")
    public String novoForm(Model model) {
        model.addAttribute("tipoInfusao", new TipoInfusao());
        return "form-tipo-infusao";
    }

    @PostMapping
    public String salvar(@ModelAttribute TipoInfusao tipoInfusao) {
        tipoInfusaoService.save(tipoInfusao);
        return "redirect:/tipos-infusao";
    }

    @GetMapping("/{id}/editar")
    public String editarForm(@PathVariable Long id, Model model) {
        model.addAttribute("tipoInfusao", tipoInfusaoService.findById(id));
        return "form-tipo-infusao";
    }

    @PostMapping("/{id}")
    public String atualizar(@PathVariable Long id, @ModelAttribute TipoInfusao tipoInfusao) {
        tipoInfusao.setId(id);
        tipoInfusaoService.save(tipoInfusao);
        return "redirect:/tipos-infusao";
    }

    @GetMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            tipoInfusaoService.deleteById(id);
        } catch (EntityInUseException e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/tipos-infusao";
    }
}
