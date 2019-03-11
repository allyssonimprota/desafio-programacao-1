package com.improtainfo.nexaaas.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.improtainfo.nexaaas.entity.Cliente;
import com.improtainfo.nexaaas.repository.ClienteRepository;

@RestController
@RequestMapping("/Clientes")
public class ClienteResource {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@GetMapping
	public ResponseEntity<?> listarClientes() {
		List<Cliente> listaClientes = clienteRepository.findAll();
		return !listaClientes.isEmpty() ? ResponseEntity.ok(listaClientes) : ResponseEntity.noContent().build(); 
	}
	
	@PostMapping
	public ResponseEntity<Cliente> criar(@RequestBody Cliente cliente, HttpServletResponse response){
		Cliente valida = clienteRepository.findByNome(cliente.getNome());
		if (valida == null || valida.getIdCliente() == null) {
			valida = clienteRepository.save(cliente);
		} 
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/idCliente")
						.buildAndExpand(valida.getIdCliente()).toUri();
		response.setHeader("Location", uri.toASCIIString());
		
		return ResponseEntity.created(uri).body(valida);
	}
	
	
	@GetMapping("/{codigo}")
	public ResponseEntity<?> buscarClientePorId(@PathVariable Long codigo) {
		Optional<Cliente> cliente = clienteRepository.findById(codigo);
		return cliente.isPresent() ?  ResponseEntity.ok(cliente) : ResponseEntity.noContent().build(); 
	}
	
}
