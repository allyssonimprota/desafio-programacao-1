package com.improtainfo.nexaaas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.improtainfo.nexaaas.entity.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

	public Empresa findByNome(String nome);
}
