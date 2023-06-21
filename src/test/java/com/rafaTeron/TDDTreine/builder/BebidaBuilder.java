package com.rafaTeron.TDDTreine.builder;

import com.rafaTeron.TDDTreine.entities.Bebida;


public class BebidaBuilder {
	private Bebida elemento;
	private BebidaBuilder(){}

	public static BebidaBuilder umBebida() {
		BebidaBuilder builder = new BebidaBuilder();
		inicializarDadosPadroes(builder);
		return builder;
	}

	public static void inicializarDadosPadroes(BebidaBuilder builder) {
		builder.elemento = new Bebida(null, null, null);
		Bebida elemento = builder.elemento;

		
		elemento.setNome("Agua");
		elemento.setEstoque(5);
		elemento.setValor(3.0);
	}

	public BebidaBuilder comNome(String param) {
		elemento.setNome(param);
		return this;
	}

	public BebidaBuilder comEstoque(Integer param) {
		elemento.setEstoque(param);
		return this;
	}

	public BebidaBuilder comValor(Double param) {
		elemento.setValor(param);
		return this;
	}

	public Bebida agora() {
		return elemento;
	}
}