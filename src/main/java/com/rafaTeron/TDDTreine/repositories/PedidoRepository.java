package com.rafaTeron.TDDTreine.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rafaTeron.TDDTreine.entities.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long>{

	public List<Pedido> obterLocacoesPendentes();
}
