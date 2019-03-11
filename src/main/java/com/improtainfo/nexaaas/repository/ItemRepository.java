package com.improtainfo.nexaaas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.improtainfo.nexaaas.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {

}
