package com.rafaTeron.TDDTreine.builder;

import java.lang.String;
import com.rafaTeron.TDDTreine.entities.Usuario;


public class UsuarioBuilder {
	private Usuario elemento;
	private UsuarioBuilder(){}

	public static UsuarioBuilder umUsuario() {
		UsuarioBuilder builder = new UsuarioBuilder();
		inicializarDadosPadroes(builder);
		return builder;
	}

	public static void inicializarDadosPadroes(UsuarioBuilder builder) {
		builder.elemento = new Usuario();
		Usuario elemento = builder.elemento;

		
		elemento.setNome("Usuario 1");
	}

	public UsuarioBuilder comNome(String param) {
		elemento.setNome(param);
		return this;
	}

	public Usuario agora() {
		return elemento;
	}
}