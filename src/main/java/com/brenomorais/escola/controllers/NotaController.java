package com.brenomorais.escola.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.brenomorais.escola.models.Aluno;
import com.brenomorais.escola.models.Nota;
import com.brenomorais.escola.repositories.AlunoRepository;

@Controller
public class NotaController {
	
	@Autowired
	private AlunoRepository repositorio;
	
	@GetMapping("/nota/cadastrar/{id}")
	public String cadastrar(@PathVariable String id, Model model) {
		Aluno aluno = repositorio.obterAlunoPor(id);
		model.addAttribute("aluno", aluno);
		model.addAttribute("nota", new Nota());

		return "/nota/cadastrar";
	}
	
	@PostMapping("/nota/salvar/{id}")
	public String salvar(@PathVariable String id, @ModelAttribute Nota nota) {
	  Aluno aluno = repositorio.obterAlunoPor(id);
	  repositorio.salvar(aluno.adicionar(aluno, nota));
	  
	  return "redirect:/aluno/listar";
	}
	
	@GetMapping("/nota/iniciarpesquisa")
	public String iniciarPesquisa() {
		return "nota/pesquisar";
	}
	
	@GetMapping("/nota/pesquisar")
	public String pesquisarPor(@RequestParam("classificacao") String classificacao, 
			@RequestParam("notacorte") String notaCorte, Model model) {
	  List<Aluno> alunos = repositorio.pesquisaPor(classificacao, Double.parseDouble(notaCorte));
	  model.addAttribute("alunos", alunos);	  
	  return "nota/pesquisar";
	}

}
