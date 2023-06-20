package com.rafaTeron.TDDTreine.entities;

import java.util.Objects;

public class Bebida {
	
	private String nome;
	private Integer estoque;
	private Double valor;

	public Bebida(String nome, Integer estoque, Double valor) {
		this.nome = nome;
		this.estoque = estoque;
		this.valor = valor;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getEstoque() {
		return estoque;
	}

	public void setEstoque(Integer estoque) {
		this.estoque = estoque;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	@Override
	public int hashCode() {
		return Objects.hash(nome);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Bebida other = (Bebida) obj;
		return Objects.equals(nome, other.nome);
	}
	
	

}
