package com.rafaTeron.TDDTreine.services;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import com.rafaTeron.TDDTreine.entities.Bebida;
import com.rafaTeron.TDDTreine.entities.Pedido;
import com.rafaTeron.TDDTreine.entities.Usuario;
import com.rafaTeron.TDDTreine.exceptions.BebidaSemEstoqueException;
import com.rafaTeron.TDDTreine.exceptions.PedidoException;
import com.rafaTeron.TDDTreine.repositories.PedidoRepository;
import com.rafaTeron.TDDTreine.utils.DataUtils;



public class PedidoService {
	
	private PedidoRepository pedidoRepository;
	private SPCService spcService;
	private EmailService emailService;
	
	private void estoqueVazio(List<Bebida> bebidas) throws BebidaSemEstoqueException {
		for(Bebida bebida: bebidas) {
			if(bebida.getEstoque() == 0) {
				throw new BebidaSemEstoqueException("Sem bebida no estoque");
			}
		}
	}

	private void semBebida(List<Bebida> bebidas) throws PedidoException {
		if (bebidas == null || bebidas.isEmpty()) {
			throw new PedidoException("Sem bebida");
		}
	}

	private void semUsuario(Usuario usuario) throws PedidoException {
		if (usuario == null) {
			throw new PedidoException("Usuario Inválido");
		}
	}
	
	private void verificarSpc(Usuario usuario) throws PedidoException {
		boolean negativado;
		try {
			negativado = spcService.possuiNegativacao(usuario);
		} catch (Exception e) {
		    throw new PedidoException("Problema com SPC , tente novamente.");
		}
		
		if(negativado) {
			throw new PedidoException("Usuario negativado");
		}
	}
	
	public Pedido comprarBebida(Usuario usuario, List<Bebida> bebidas) throws PedidoException,BebidaSemEstoqueException {
		semUsuario(usuario);
		semBebida(bebidas);
		estoqueVazio(bebidas);
		
		verificarSpc(usuario);
		
		Pedido pedido = new Pedido();
		pedido.setUsuario(usuario);
		pedido.setPedido(bebidas);
		pedido.setDataInicio(dataHoje());
		Double valorTotal = calcularValorPedido(bebidas);
		pedido.setValor(valorTotal);
		
		LocalDate dataEntrega = dataHoje();
	    dataEntrega = DataUtils.adicionarDias(dataEntrega, 6);
		
		if (dataEntrega.getDayOfWeek() == DayOfWeek.SUNDAY) {
			dataEntrega = DataUtils.adicionarDias(dataEntrega, 1);
		}
		pedido.setDataFinalEntrega(dataEntrega);
		
		pedidoRepository.save(pedido);
		
		return pedido;
	}


	private Double calcularValorPedido(List<Bebida> bebidas) {
		Double valorTotal = 0d;
		for(int i = 0; i < bebidas.size(); i++) {
			Bebida bebida = bebidas.get(i);
			Double valorFilme = bebida.getValor();
			valorTotal += valorFilme;
		}
		return valorTotal;
	}

	protected LocalDate dataHoje() {
		return LocalDate.now();
	}
	
	public void notificarAtrasos(){
		List<Pedido> pedido = pedidoRepository.obterLocacoesPendentes();
		for(Pedido x : pedido) {
			if(x.getDataFinalEntrega().isBefore(dataHoje())) {
				emailService.notificarAtraso(x.getUsuario());
			}
		}
	}
	
	public void prorrogarPedido(Pedido pedido, int dias) {
		Pedido novoPedido = new Pedido();
		novoPedido.setUsuario(pedido.getUsuario());
		novoPedido.setPedido(pedido.getPedido());
		novoPedido.setDataInicio(dataHoje());
		novoPedido.setDataFinalEntrega(DataUtils.obterDataComDiferencaDias(dias));
		novoPedido.setValor(pedido.getValor() * dias);
		pedidoRepository.save(novoPedido);
	}

}
