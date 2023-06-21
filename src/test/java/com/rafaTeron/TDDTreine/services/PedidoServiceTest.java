package com.rafaTeron.TDDTreine.services;

import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.rules.ErrorCollector;

public class PedidoServiceTest {
	
	private PedidoService pedido;
	
	@Rule
	private ErrorCollector error = new ErrorCollector();
	
	@BeforeEach
	public void setup(){
		pedido = new PedidoService();
	}
	
	@Test
	public void testPedido() {
		//açao
	   
		
	}

}
