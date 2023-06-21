package com.rafaTeron.TDDTreine.services;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.rafaTeron.TDDTreine.entities.Bebida;
import com.rafaTeron.TDDTreine.entities.Pedido;
import com.rafaTeron.TDDTreine.entities.Usuario;
import com.rafaTeron.TDDTreine.exceptions.BebidaSemEstoqueException;
import com.rafaTeron.TDDTreine.exceptions.PedidoException;
import com.rafaTeron.TDDTreine.utils.DataUtils;

import buildermaster.BuilderMaster;



public class PedidoService {
	
	public Pedido comprarBebida(Usuario usuario, List<Bebida> bebidas) throws BebidaSemEstoqueException, PedidoException {
		if (usuario == null) {
			throw new PedidoException("Usuario Inválido");
		}
		if (bebidas == null || bebidas.isEmpty()) {
			throw new PedidoException("Sem bebida");
		}
		for(Bebida bebida: bebidas) {
			if(bebida.getEstoque() == 0) {
				throw new BebidaSemEstoqueException();
			}
		}
		
		Pedido pedido = new Pedido();
		pedido.setUsuario(usuario);
		pedido.setPedido(bebidas);
		pedido.setDataInicio(new Date());
		Double valorTotal = 0d;
		for(int i = 0; i < bebidas.size(); i++) {
			Bebida bebida = bebidas.get(i);
			Double valorFilme = bebida.getValor();
			valorTotal += valorFilme;
		}
		pedido.setValor(valorTotal);
		
		Date dataEntrega = new Date();
		dataEntrega = DataUtils.adicionarDias(dataEntrega, 2);
		if (DataUtils.verificarDiaSemana(dataEntrega, Calendar.SUNDAY)) {
			dataEntrega = DataUtils.adicionarDias(dataEntrega, 1);
		}
		pedido.setDataFinalEntrega(dataEntrega);
		
		return pedido;
	}
	
	public static void main(String[] args) {
		new BuilderMaster().gerarCodigoClasse(Bebida.class);
	}

}
