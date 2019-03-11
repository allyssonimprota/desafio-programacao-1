package com.improtainfo.nexaaas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.improtainfo.nexaaas.repository.ClienteRepository;
import com.improtainfo.nexaaas.repository.EmpresaRepository;

@Controller
@RequestMapping("/vendas")
public class VendasController {

	@Autowired
	private EmpresaRepository empresaRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@RequestMapping
	public ModelAndView pesquisa() {
		ModelAndView mv = new ModelAndView("/empresa/PesquisaEmpresas");
		mv.addObject("empresas", empresaRepository.findAll());
		mv.addObject("clientes", clienteRepository.findAll());
		return mv;
	}

}
