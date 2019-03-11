package com.improtainfo.nexaaas.controller;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.improtainfo.nexaaas.service.ImportacaoService;

@Controller
@RequestMapping("/importacao")
public class ImportacaoController {

	@Autowired
	private ImportacaoService importacaoService;

	// Diretório temporário para tratar o arquivo.
	private static String UPLOADED_FOLDER = "c://DEV//";

	@PostMapping("/upload")
	public ModelAndView singleFileUpload(@RequestParam("file") MultipartFile file,
			RedirectAttributes redirectAttributes) {

		//Validar se o arquivo está selecionado
		if (file.isEmpty()) {
			redirectAttributes.addFlashAttribute("messageErro", "Por favor, selecione um  arquivo para importação!");
			return new ModelAndView("redirect:/vendas");
		}
		
		try {

			// 	Obter o arquivo e Salvar no disco
			byte[] bytes = file.getBytes();
			Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
			Files.write(path, bytes);
			
			//	Ler o arquivo e importar
			File arquivo = new File(UPLOADED_FOLDER + file.getOriginalFilename());
			
			//Validar se o arquivo é um txt
			if (!arquivo.getName().toLowerCase().endsWith(".txt")) {
				redirectAttributes.addFlashAttribute("messageErro", "Arquivo inválido - Por favor, selecione um  arquivo de texto para importação!");
				return new ModelAndView("redirect:/vendas");
			}
			
			BigDecimal valorTotal = importacaoService.processarImportacao(arquivo);
			
			redirectAttributes.addFlashAttribute("message", mensagemSucesso(file, valorTotal));

		} catch (IndexOutOfBoundsException iobe) {
			redirectAttributes.addFlashAttribute("messageErro", "Arquivo inválido - Arquivo com formatação incorreta!");
			return new ModelAndView("redirect:/vendas");
		} catch (IOException e) {
			redirectAttributes.addFlashAttribute("messageErro", "Arquivo inválido - Erro ao abrir Arquivo!");
			return new ModelAndView("redirect:/vendas");
		}

		return new ModelAndView("redirect:/vendas");
	}

	private String mensagemSucesso(MultipartFile file, BigDecimal valorTotal) {
		Locale locale = new Locale("pt", "BR");
		NumberFormat formatoMoeda = NumberFormat.getCurrencyInstance(locale);
		
		String menssagem = " O Arquivo " + file.getOriginalFilename() + " importado com sucesso! ";
		menssagem += "A receita bruta total é: " + formatoMoeda.format(valorTotal.doubleValue());
		
		return menssagem;
	}

}
