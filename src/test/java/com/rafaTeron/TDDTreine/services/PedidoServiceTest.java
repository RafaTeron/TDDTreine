package com.rafaTeron.TDDTreine.services;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.rafaTeron.TDDTreine.builder.BebidaBuilder;
import com.rafaTeron.TDDTreine.builder.UsuarioBuilder;
import com.rafaTeron.TDDTreine.entities.Bebida;
import com.rafaTeron.TDDTreine.entities.Pedido;
import com.rafaTeron.TDDTreine.entities.Usuario;
import com.rafaTeron.TDDTreine.exceptions.BebidaSemEstoqueException;
import com.rafaTeron.TDDTreine.exceptions.PedidoException;
import com.rafaTeron.TDDTreine.matchers.MatchersProprios;

public class PedidoServiceTest {

	private PedidoService pedidoService;

	@BeforeEach
	public void setup() {
		pedidoService = new PedidoService();
	}

	@Test
	public void pedidoBebida() throws Exception {
		//Assumptions.assumeFalse(LocalDate.now().getDayOfWeek() == DayOfWeek.SUNDAY);
		// cenario
		Usuario usuario = UsuarioBuilder.umUsuario().agora();
		List<Bebida> bebidas = List.of(BebidaBuilder.umBebida().agora());

		// acao
		Pedido pedido = pedidoService.comprarBebida(usuario, bebidas);

		// verificacao
		Assertions.assertAll("Pedido de Bebida", 
				() -> Assertions.assertEquals(pedido.getValor(), 3.0),
				() -> Assertions.assertEquals(pedido.getDataInicio(), LocalDate.now()));
	}

	@Test
	public void naoDeveComprarBebidaSemEstoque() throws Exception {

		// cenario
		Usuario usuario = UsuarioBuilder.umUsuario().agora();
		List<Bebida> bebidas = List.of(BebidaBuilder.umBebida().comEstoque(0).agora());

		// Ação
		try {
			pedidoService.comprarBebida(usuario, bebidas);
			Assertions.fail("Tem bebida no estoque");
		} catch (BebidaSemEstoqueException e) {
			Assertions.assertEquals(e.getMessage(), "Sem bebida no estoque");
		}

	}

	@Test
	public void naoDeveComprarBebidaSemUsuario() throws Exception {
		// cenario
		Usuario usuario = UsuarioBuilder.umUsuario().agora();

		// Ação
		try {
			pedidoService.comprarBebida(usuario, null);
			Assertions.fail("Tem Bebida");
		} catch (PedidoException e) {
			Assertions.assertEquals(e.getMessage(), "Sem bebida");
		}
	}

	@Test
	public void naoDeveComprarBebidaSemBebida() throws Exception {
		// cenario
		List<Bebida> bebidas = List.of(BebidaBuilder.umBebida().comEstoque(1).agora());

		// Ação
		try {
			pedidoService.comprarBebida(null, bebidas);
			Assertions.fail("Usuario Ativo");
		} catch (PedidoException e) {
			Assertions.assertEquals(e.getMessage(), "Usuario Inválido");
		}
	}

	@Test
	public void naoEntregaNoDomingo() throws BebidaSemEstoqueException, PedidoException {
		// cenario
		Usuario usuario = UsuarioBuilder.umUsuario().agora();
		List<Bebida> bebidas = List.of(BebidaBuilder.umBebida().agora());

		// acao
		Pedido pedido = pedidoService.comprarBebida(usuario, bebidas);

		if (pedido.getDataFinalEntrega().getDayOfWeek() == DayOfWeek.MONDAY) {
			Assertions.assertTrue(MatchersProprios.caiNumaSegunda().matches(pedido.getDataFinalEntrega()));
		}
	}

}