package com.rafaTeron.TDDTreine.builder;

import com.rafaTeron.TDDTreine.entities.Usuario;
import java.util.Arrays;
import java.lang.Double;
import java.util.Date;

import com.rafaTeron.TDDTreine.entities.Bebida;
import com.rafaTeron.TDDTreine.entities.Pedido;


public class PedidoBuilder {
	private Pedido elemento;
	private PedidoBuilder(){}

	public static PedidoBuilder umPedido() {
		PedidoBuilder builder = new PedidoBuilder();
		inicializarDadosPadroes(builder);
		return builder;
	}

	public static void inicializarDadosPadroes(PedidoBuilder builder) {
		builder.elemento = new Pedido();
		Pedido elemento = builder.elemento;

		
		elemento.setUsuario(UsuarioBuilder.umUsuario().agora());
		elemento.setPedido(Arrays.asList(BebidaBuilder.umBebida().agora()));
		elemento.setDataInicio(new Date());
		elemento.setDataFinalEntrega(null);
		elemento.setValor(3.0);
	}

	public PedidoBuilder comUsuario(Usuario param) {
		elemento.setUsuario(param);
		return this;
	}

	public PedidoBuilder comListaBebida(Bebida... params) {
		elemento.setPedido(Arrays.asList(params));
		return this;
	}

	public PedidoBuilder comDataInicioPedido(Date param) {
		elemento.setDataInicio(param);
		return this;
	}

	public PedidoBuilder comDataFinalEntrega(Date param) {
		elemento.setDataFinalEntrega(param);
		return this;
	}

	public PedidoBuilder comValor(Double param) {
		elemento.setValor(param);
		return this;
	}

	public Pedido agora() {
		return elemento;
	}
}
