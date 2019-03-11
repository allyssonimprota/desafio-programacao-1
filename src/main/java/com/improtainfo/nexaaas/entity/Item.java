package com.improtainfo.nexaaas.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Item implements Serializable {

	private static final long serialVersionUID = 2853111040393772035L;
	
	private Long idItem;
	
	private Pedido pedido;
	
	private Produto produto;
	
	private Integer quantidade;
	
	private BigDecimal valorTotalItem;

	/**
	 * @return the idItens
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id_Item", unique = true, nullable = false)
	public Long getIdItem() {
		return idItem;
	}

	/**
	 * @param idItens the idItens to set
	 */
	public void setIdItem(Long idItem) {
		this.idItem = idItem;
	}

	/**
	 * @return the pedido
	 */
	@ManyToOne
    @JoinColumn(name="idPedido", nullable=false)
	public Pedido getPedido() {
		return pedido;
	}

	/**
	 * @param pedido the pedido to set
	 */
	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	/**
	 * @return the produto
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idProduto", insertable = false, updatable = false)
	public Produto getProduto() {
		return produto;
	}

	/**
	 * @param produto the produto to set
	 */
	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	/**
	 * @return the quantidade
	 */
	public Integer getQuantidade() {
		return quantidade;
	}

	/**
	 * @param quantidade the quantidade to set
	 */
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	/**
	 * @return the valorTotalItem
	 */
	public BigDecimal getValorTotalItem() {
		return valorTotalItem;
	}

	/**
	 * @param valorTotalItem the valorTotalItem to set
	 */
	public void setValorTotalItem(BigDecimal valorTotalItem) {
		this.valorTotalItem = valorTotalItem;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return Objects.hash(idItem);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Item)) {
			return false;
		}
		Item other = (Item) obj;
		return Objects.equals(idItem, other.idItem);
	}
 	
}
