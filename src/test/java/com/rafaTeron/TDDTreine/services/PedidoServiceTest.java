package com.rafaTeron.TDDTreine.services;

import static org.mockito.Mockito.times;

import java.lang.reflect.Method;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import com.rafaTeron.TDDTreine.builder.BebidaBuilder;
import com.rafaTeron.TDDTreine.builder.PedidoBuilder;
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
	@Spy
	private PedidoService pedidoService;

	@Mock
	private SPCService spcService;

	@Mock
	private EmailService emailService;

	@Mock
	private PedidoRepository pedidoRepository;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void pedidoBebida() throws Exception {
		// Assumptions.assumeFalse(LocalDate.now().getDayOfWeek() == DayOfWeek.SUNDAY);
		// cenario
		Usuario usuario = UsuarioBuilder.umUsuario().agora();
		List<Bebida> bebidas = List.of(BebidaBuilder.umBebida().agora());

		// acao
		Pedido pedido = pedidoService.comprarBebida(usuario, bebidas);

		// verificacao
		Assertions.assertAll("Pedido de Bebida", () -> Assertions.assertEquals(3.0, pedido.getValor()),
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

	// @Test
	public void naoDeveComprarBebidaSemUsuario() throws Exception {
		// cenario
		List<Bebida> bebidas = List.of(BebidaBuilder.umBebida().comEstoque(1).agora());

		try {
			// Ação
			pedidoService.comprarBebida(null, bebidas);
			// Verificaçao
			Assertions.fail("Usuario Ativo");
		} catch (PedidoException e) {
			// Verificaçao
			Assertions.assertEquals("Usuario Inválido", e.getMessage());
		}
	}

	@Test
	public void naoDeveComprarBebidaSemBebida() throws Exception {
		// cenario
		Usuario usuario = UsuarioBuilder.umUsuario().agora();

		try {
			// Ação
			pedidoService.comprarBebida(usuario, null);
			// Verificaçao
			Assertions.fail("Tem Bebida");
		} catch (PedidoException e) {
			// Verificaçao
			Assertions.assertEquals("Sem bebida", e.getMessage());
		}
	}
	
	@Test
	public void naoDeveComprarBebidaComListaVazia() throws Exception {
		// cenario
		Usuario usuario = UsuarioBuilder.umUsuario().agora();
		List<Bebida> bebidas = new ArrayList<>();

		try {
			// Ação
			pedidoService.comprarBebida(usuario, bebidas);
			// Verificaçao
			Assertions.fail("Tem Bebida");
		} catch (PedidoException e) {
			// Verificaçao
			Assertions.assertEquals("Sem bebida", e.getMessage());
		}
	}

	@Test
	public void naoEntregaNoDomingo() throws BebidaSemEstoqueException, PedidoException {
		// cenario
		Usuario usuario = UsuarioBuilder.umUsuario().agora();
		List<Bebida> bebidas = List.of(BebidaBuilder.umBebida().agora());

		Mockito.when(pedidoService.dataHoje()).thenReturn(LocalDate.of(2023, 6, 26));

		// acao
		Pedido pedido = pedidoService.comprarBebida(usuario, bebidas);

		Assertions.assertEquals(DayOfWeek.MONDAY, pedido.getDataFinalEntrega().getDayOfWeek());
	}

	@Test
	public void naoDevePedirBebidaParaNegativadoSPC() throws Exception {
		// cenario
		Usuario usuario = UsuarioBuilder.umUsuario().agora();
		List<Bebida> bebidas = List.of(BebidaBuilder.umBebida().agora());

		Mockito.when(spcService.possuiNegativacao(Mockito.any(Usuario.class))).thenReturn(true);

		// açao
		try {
			pedidoService.comprarBebida(usuario, bebidas);
			// verificaçao
			Assertions.fail();
		} catch (PedidoException e) {
			Assertions.assertEquals("Usuario negativado", e.getMessage());
		}

		Mockito.verify(spcService).possuiNegativacao(usuario);
	}

	@Test
	public void deveEnviarEmailParaPedidosAtrasadas() {
		// cenario
		Usuario usuario = UsuarioBuilder.umUsuario().agora();
		List<Pedido> pedidos = List.of(PedidoBuilder.umPedido().atrasada().comUsuario(usuario).agora());
		Mockito.when(pedidoRepository.obterLocacoesPendentes()).thenReturn(pedidos);
		// açao
		pedidoService.notificarAtrasos();
		// verificaçao
		// times() é o numero de verificaçoes
		Mockito.verify(emailService, times(1)).notificarAtraso(Mockito.any(Usuario.class));
		Mockito.verify(emailService).notificarAtraso(usuario);
		Mockito.verifyNoMoreInteractions(emailService);
	}
	
	@Test
	public void naoDeveEnviarEmailParaPedidosNaoAtrasados() {
	    // cenario
	    Usuario usuario = UsuarioBuilder.umUsuario().agora();
	    List<Pedido> pedidos = List.of(PedidoBuilder.umPedido().comUsuario(usuario).agora());
	    Mockito.when(pedidoRepository.obterLocacoesPendentes()).thenReturn(pedidos);
	    // ação
	    pedidoService.notificarAtrasos();
	    // verificação
	    Mockito.verifyNoInteractions(emailService);
	}

	@Test
	public void deveTratarErronoSPC() throws Exception {
		// cenario
		Usuario usuario = UsuarioBuilder.umUsuario().agora();
		List<Bebida> bebidas = List.of(BebidaBuilder.umBebida().agora());
		Mockito.when(spcService.possuiNegativacao(usuario)).thenThrow(new Exception("Falha"));

		// açao
		try {
			pedidoService.comprarBebida(usuario, bebidas);
			// verificaçao
			Assertions.fail();
		} catch (PedidoException e) {
			Assertions.assertEquals("Problema com SPC , tente novamente.", e.getMessage());
		}
	}

	@Test
	public void deveProrrogarUmPedido() {
		// cenario
		Pedido pedidos = PedidoBuilder.umPedido().agora();
		// açao
		pedidoService.prorrogarPedido(pedidos, 3);
		// verificaçao
		ArgumentCaptor<Pedido> argCapt = ArgumentCaptor.forClass(Pedido.class);
		Mockito.verify(pedidoRepository).save(argCapt.capture());
		Pedido pedidoRetornada = argCapt.getValue();

		Assertions.assertAll("Prorrogar Pedido", () -> Assertions.assertEquals(9.0, pedidoRetornada.getValor()),
				() -> Assertions.assertEquals(LocalDate.now(), pedidoRetornada.getDataInicio()),
				() -> Assertions.assertTrue(
						MatchersProprios.ehHojeComDiferencaDias(3).matches(pedidoRetornada.getDataFinalEntrega())));

	}

	@Test
	public void deveCalcularValorPedido() throws Exception {
		// Cenário
		List<Bebida> bebidas = List.of(BebidaBuilder.umBebida().agora());

		// Ação
		PedidoService pedidoService = Mockito.mock(PedidoService.class);
		Method metodo = PedidoService.class.getDeclaredMethod("calcularValorPedido", List.class);
		metodo.setAccessible(true);
		Double valor = (Double) metodo.invoke(pedidoService, bebidas);

		// Verificação
		Assertions.assertEquals(3.0, valor);
	}

}