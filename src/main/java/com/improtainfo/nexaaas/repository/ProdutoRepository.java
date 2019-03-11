package com.improtainfo.nexaaas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.improtainfo.nexaaas.entity.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
	
	public Produto findByDescricao(String nome);

}
