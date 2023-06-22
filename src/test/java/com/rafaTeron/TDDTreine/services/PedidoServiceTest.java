package com.rafaTeron.TDDTreine.services;



import java.time.LocalDate;
import java.util.List;

import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.rules.ErrorCollector;

import com.rafaTeron.TDDTreine.builder.BebidaBuilder;
import com.rafaTeron.TDDTreine.builder.UsuarioBuilder;
import com.rafaTeron.TDDTreine.entities.Bebida;
import com.rafaTeron.TDDTreine.entities.Pedido;
import com.rafaTeron.TDDTreine.entities.Usuario;
import com.rafaTeron.TDDTreine.matchers.MatchersProprios;



public class PedidoServiceTest {
	
	private PedidoService pedidoService;
	
	@Rule
	private ErrorCollector error = new ErrorCollector();
	
	@BeforeEach
	public void setup(){
		pedidoService = new PedidoService();
	}
	
	@Test
	public void pedidoBebida() throws Exception {
		//açao
	    Usuario usuario = UsuarioBuilder.umUsuario().agora();
	    List<Bebida> bebidas = List.of(BebidaBuilder.umBebida().agora());

		//acao
		Pedido pedido = pedidoService.comprarBebida(usuario, bebidas);
			
		//verificacao
		Assertions.assertEquals(pedido.getValor(), 3.0);
		Assertions.assertEquals( pedido.getDataInicio(),MatchersProprios.hoje());
		Assertions.assertEquals(pedido.getDataFinalEntrega() ,LocalDate.now().plusDays(2));
	}

}
