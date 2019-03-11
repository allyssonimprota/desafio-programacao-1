package com.improtainfo.nexaaas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.improtainfo.nexaaas.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	public Cliente findByNome(String nome);
	
}
