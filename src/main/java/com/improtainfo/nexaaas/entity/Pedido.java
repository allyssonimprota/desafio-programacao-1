package com.improtainfo.nexaaas.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Pedido implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6946828608486377967L;

	private Long idPedido;
	
	private List<Item> itensList;

	private Empresa empresa;

	private Cliente cliente;
	
	private BigDecimal valorTotalPedido;

	/**
	 * @return the idPedido
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id_pedido", unique = true, nullable = false)
	public Long getIdPedido() {
		return idPedido;
	}

	/**
	 * @param idPedido the idPedido to set
	 */
	public void setIdPedido(Long idPedido) {
		this.idPedido = idPedido;
	}

	/**
	 * @return the itensList
	 */
	@OneToMany(cascade=CascadeType.ALL, mappedBy="pedido")
	public List<Item> getItensList() {
		return itensList;
	}

	/**
	 * @param itensList the itensList to set
	 */
	public void setItensList(List<Item> itensList) {
		this.itensList = itensList;
	}

	/**
	 * @return the empresa
	 */
	@ManyToOne(targetEntity = Empresa.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "idEmpresa")
	public Empresa getEmpresa() {
		return empresa;
	}

	/**
	 * @param empresa the empresa to set
	 */
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	/**
	 * @return the cliente
	 */
	@ManyToOne(targetEntity = Cliente.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "idCliente")
	public Cliente getCliente() {
		return cliente;
	}

	/**
	 * @param cliente the cliente to set
	 */
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	/**
	 * @return the valorTotalPedido
	 */
	public BigDecimal getValorTotalPedido() {
		return valorTotalPedido;
	}

	/**
	 * @param valorTotalPedido the valorTotalPedido to set
	 */
	public void setValorTotalPedido(BigDecimal valorTotalPedido) {
		this.valorTotalPedido = valorTotalPedido;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return Objects.hash(idPedido);
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
		if (!(obj instanceof Pedido)) {
			return false;
		}
		Pedido other = (Pedido) obj;
		return Objects.equals(idPedido, other.idPedido);
	}

}
