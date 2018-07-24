package com.brenomorais.escola.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.brenomorais.escola.models.Aluno;
import com.brenomorais.escola.repositories.AlunoRepository;

@Controller
public class AlunoController {
	
	@Autowired
	private AlunoRepository repositorio;
	
	
	@GetMapping("/aluno/cadastrar")
	public String cadastrar(Model model) {		
		model.addAttribute("aluno", new Aluno());
		return "aluno/cadastrar";
	}
	
	@PostMapping("/aluno/salvar")
	public String salvar(@ModelAttribute Aluno aluno) {
		System.out.println(aluno);

		repositorio.salvar(aluno);		
		return "redirect:/";
	}

}
