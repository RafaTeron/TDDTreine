package com.rafaTeron.TDDTreine.entities;

import java.time.LocalDate;
import java.util.List;

public class Pedido {
	
	private Usuario usuario;
	private List<Bebida> bebida;
	private LocalDate dataInicioPedido;
	private LocalDate dataFinalEntrega;
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

	public LocalDate getDataInicio() {
		return dataInicioPedido;
	}

	public void setDataInicio(LocalDate dataInicio) {
		this.dataInicioPedido = dataInicio;
	}

	public LocalDate getDataFinalEntrega() {
		return dataFinalEntrega;
	}

	public void setDataFinalEntrega(LocalDate dataFinalEntrega) {
		this.dataFinalEntrega = dataFinalEntrega;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public void setBebida(List<Object> asList) {
		// TODO Auto-generated method stub
		
	}
	
	

}
