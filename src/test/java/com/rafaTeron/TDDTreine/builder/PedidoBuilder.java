package com.rafaTeron.TDDTreine.builder;

import java.time.LocalDate;
import java.util.Arrays;

import com.rafaTeron.TDDTreine.entities.Bebida;
import com.rafaTeron.TDDTreine.entities.Pedido;
import com.rafaTeron.TDDTreine.entities.Usuario;
import com.rafaTeron.TDDTreine.utils.DataUtils;


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
		elemento.setDataInicio(LocalDate.now());
		elemento.setDataFinalEntrega(DataUtils.obterDataComDiferencaDias(2));
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

	public PedidoBuilder comDataInicioPedido(LocalDate param) {
		elemento.setDataInicio(param);
		return this;
	}

	public PedidoBuilder comDataFinalEntrega(LocalDate param) {
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
