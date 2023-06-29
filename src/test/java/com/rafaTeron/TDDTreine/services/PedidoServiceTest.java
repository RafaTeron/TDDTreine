package com.rafaTeron.TDDTreine.services;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.rafaTeron.TDDTreine.builder.BebidaBuilder;
import com.rafaTeron.TDDTreine.builder.UsuarioBuilder;
import com.rafaTeron.TDDTreine.entities.Bebida;
import com.rafaTeron.TDDTreine.entities.Pedido;
import com.rafaTeron.TDDTreine.entities.Usuario;
import com.rafaTeron.TDDTreine.exceptions.BebidaSemEstoqueException;
import com.rafaTeron.TDDTreine.exceptions.PedidoException;
import com.rafaTeron.TDDTreine.matchers.MatchersProprios;
import com.rafaTeron.TDDTreine.repositories.PedidoRepository;

public class PedidoServiceTest {

	@InjectMocks
	private PedidoService pedidoService;
	
	@Mock
	private SPCService spcService;
	
	@Mock
	private PedidoRepository pedidoRepository;
	
	@BeforeEach
	public void setup() {
		 MockitoAnnotations.openMocks(this);
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
				() -> Assertions.assertEquals(3.0, pedido.getValor()),
				() -> Assertions.assertEquals(LocalDate.now(), pedido.getDataInicio()));
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
			Assertions.assertEquals("Sem bebida no estoque", e.getMessage());
		}

	}

	@Test
	public void naoDeveComprarBebidaSemUsuario() throws Exception {
		// cenario
		Usuario usuario = UsuarioBuilder.umUsuario().agora();

		
		try {
			// Ação
			pedidoService.comprarBebida(usuario, null);
			//Verificaçao
			Assertions.fail("Tem Bebida");
		} catch (PedidoException e) {
			//Verificaçao
			Assertions.assertEquals("Sem bebida" ,e.getMessage());
		}
	}

	@Test
	public void naoDeveComprarBebidaSemBebida() throws Exception {
		// cenario
		List<Bebida> bebidas = List.of(BebidaBuilder.umBebida().comEstoque(1).agora());

		
		try {
			// Ação
			pedidoService.comprarBebida(null, bebidas);
			//Verificaçao
			Assertions.fail("Usuario Ativo");
		} catch (PedidoException e) {
			//Verificaçao
			Assertions.assertEquals( "Usuario Inválido" ,e.getMessage());
		}
	}

	@Test
	public void naoEntregaNoDomingo() throws BebidaSemEstoqueException, PedidoException {
		// cenario
		Usuario usuario = UsuarioBuilder.umUsuario().agora();
		List<Bebida> bebidas = List.of(BebidaBuilder.umBebida().agora());

		// acao
		Pedido pedido;
		try {
			pedido = pedidoService.comprarBebida(usuario, bebidas);
			//verificaçao
			if (pedido.getDataFinalEntrega().getDayOfWeek() == DayOfWeek.MONDAY) {
				Assertions.assertTrue(MatchersProprios.caiNumaSegunda().matches(pedido.getDataFinalEntrega()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	@Test
	public void naoDevePedirBebidaParaNegativadoSPC() throws PedidoException,BebidaSemEstoqueException {
		//cenario
		Usuario usuario = UsuarioBuilder.umUsuario().agora();
		List<Bebida> bebidas = List.of(BebidaBuilder.umBebida().agora());

		try {
			Mockito.when(spcService.possuiNegativacao(Mockito.any(Usuario.class))).thenReturn(true);
		} catch (Exception e) {
			throw new PedidoException("Problema com SPC , tente novamente.");
		}
		//açao
		try {
			pedidoService.comprarBebida(usuario, bebidas);
			//verificaçao
			Assertions.fail();
		} catch (PedidoException e) {
			Assertions.assertEquals( "Usuario negativado" ,e.getMessage());
		}
		
		
	}

}