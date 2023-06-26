package com.rafaTeron.TDDTreine.services;

import java.time.DayOfWeek;
import java.time.LocalDate;
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
				throw new BebidaSemEstoqueException("Sem bebida no estoque");
			}
		}
		
		Pedido pedido = new Pedido();
		pedido.setUsuario(usuario);
		pedido.setPedido(bebidas);
		pedido.setDataInicio(LocalDate.now());
		Double valorTotal = 0d;
		for(int i = 0; i < bebidas.size(); i++) {
			Bebida bebida = bebidas.get(i);
			Double valorFilme = bebida.getValor();
			valorTotal += valorFilme;
		}
		pedido.setValor(valorTotal);
		
		LocalDate dataEntrega = LocalDate.now();
	    dataEntrega = DataUtils.adicionarDias(dataEntrega, 2);
		
		if (dataEntrega.getDayOfWeek() == DayOfWeek.SUNDAY) {
			dataEntrega = DataUtils.adicionarDias(dataEntrega, 1);
		}
		pedido.setDataFinalEntrega(dataEntrega);
		
		return pedido;
	}
	
	public static void main(String[] args) {
		new BuilderMaster().gerarCodigoClasse(Bebida.class);
	}

}
