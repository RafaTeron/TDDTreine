package com.rafaTeron.TDDTreine.entities;

import java.util.Date;
import java.util.List;

public class Pedido {
	
	private Usuario usuario;
	private List<Bebida> bebida;
	private Date dataInicioPedido;
	private Date dataFinalEntrega;
	private Double valor;
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Bebida> getPedido() {
		return bebida;
	}

	public void setPedido(List<Bebida> bebida) {
		this.bebida = bebida;
	}

	public Date getDataInicio() {
		return dataInicioPedido;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicioPedido = dataInicio;
	}

	public Date getDataFinalEntrega() {
		return dataFinalEntrega;
	}

	public void setDataFinalEntrega(Date dataFinalEntrega) {
		this.dataFinalEntrega = dataFinalEntrega;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}
	
	

}
