package com.improtainfo.nexaaas.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.improtainfo.nexaaas.entity.Cliente;
import com.improtainfo.nexaaas.entity.Empresa;
import com.improtainfo.nexaaas.entity.Item;
import com.improtainfo.nexaaas.entity.Pedido;
import com.improtainfo.nexaaas.entity.Produto;
import com.improtainfo.nexaaas.repository.ClienteRepository;
import com.improtainfo.nexaaas.repository.EmpresaRepository;
import com.improtainfo.nexaaas.repository.ItemRepository;
import com.improtainfo.nexaaas.repository.PedidoRepository;
import com.improtainfo.nexaaas.repository.ProdutoRepository;

@Service
public class ImportacaoService {

	@Autowired
	private ProdutoRepository produtos;

	@Autowired
	private ClienteRepository clientes;

	@Autowired
	private EmpresaRepository empresas;

	@Autowired
	private ItemRepository itens;

	@Autowired
	private PedidoRepository pedidos;

	/**	Método que processa o arquivo e retorna com o valor bruto do arquivo importado.	*/
	public BigDecimal processarImportacao(File arquivo) throws IndexOutOfBoundsException {

		//	Transforma o arquivo num Array de linhas
		List<String[]> linhas = processarImportacaoArquivo(arquivo);
		
		//	Processa e persiste as linhas normalizadas na base
		BigDecimal valorTotal = persistirLinhas(linhas);

		return valorTotal;
	}

	public List<String[]> processarImportacaoArquivo(File arquivo) {
		BufferedReader bufferedReader;
		boolean primeiraLinha = true;
		List<String[]> linha = new ArrayList<String[]>();

		try {
			InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(arquivo));
			bufferedReader = new BufferedReader(inputStreamReader);

			String linhaArquivo = bufferedReader.readLine();
			while (linhaArquivo != null) {
				if (primeiraLinha) {
					linhaArquivo = bufferedReader.readLine();
					primeiraLinha = false;
					continue;
				}
				String[] columns = linhaArquivo.split("\t");
				linha.add(columns);
				linhaArquivo = bufferedReader.readLine();
			}
			System.out.println(linha.size());

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return linha;
	}

	public BigDecimal persistirLinhas(List<String[]> linhas) throws IndexOutOfBoundsException {
		BigDecimal valorTotalArquivo = BigDecimal.ZERO;
		List<Item> itensList;

		for (String[] linha : linhas) {
			
			if (linha.length != 6) {
				throw new IndexOutOfBoundsException();
			}
			
			Cliente cliente = persistirCliente(linha);
			Produto produto = persistirProduto(linha);
			Empresa empresa = persistirEmpresa(linha);
			Pedido pedido = persistirPedido(cliente, empresa);
			Item item = persistirItem(linha, produto, pedido);

			/** Adicionando o valor para retorno */
			valorTotalArquivo = valorTotalArquivo.add(item.getValorTotalItem());

			/** Poderia validar se já existe linha para esse cliente e essa Empresa
			 * Utilizaria para criar um único pedido com vários itens para a mesma empresa e cliente
			 */
			itensList = new ArrayList<Item>();

			itensList.add(item);
			pedido.setItensList(itensList);
			pedidos.save(pedido);
		}
		return valorTotalArquivo;
	}

	private Item persistirItem(String[] linha, Produto produto, Pedido pedido) {
		Item item = new Item();
		item.setPedido(pedido);
		item.setProduto(produto);
		item.setQuantidade(Integer.valueOf(linha[3]));
		item.setValorTotalItem(produto.getPreco().multiply(new BigDecimal(item.getQuantidade())));
		itens.save(item);
		return item;
	}

	private Pedido persistirPedido(Cliente cliente, Empresa empresa) {
		Pedido pedido = new Pedido();
		pedido.setCliente(cliente);
		pedido.setEmpresa(empresa);
		pedidos.save(pedido);
		return pedido;
	}

	private Empresa persistirEmpresa(String[] linha) {
		Empresa empresa = empresas.findByNome(linha[5]);
		if (empresa == null || empresa.getIdEmpresa() == null) {
			empresa = new Empresa();
			empresa.setNome(linha[5]);
			empresa.setEndereco(linha[4]);
			empresas.save(empresa);
		}
		return empresa;
	}

	private Produto persistirProduto(String[] linha) {
		Produto produto = produtos.findByDescricao(linha[1]);
		if (produto == null || produto.getIdProduto() == null) {
			produto = new Produto();
			produto.setDescricao(linha[1]);
			produto.setPreco(new BigDecimal(linha[2]));
			produtos.save(produto);
		}
		return produto;
	}

	private Cliente persistirCliente(String[] linha) {
		Cliente cliente = clientes.findByNome(linha[0]);
		if (cliente == null || cliente.getIdCliente() == null) {
			cliente = new Cliente();
			cliente.setNome(linha[0]);
			clientes.save(cliente);
		}
		return cliente;
	}

}
