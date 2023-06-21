package com.rafaTeron.TDDTreine.services;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.rafaTeron.TDDTreine.exceptions.NaoPodeDividirPorZeroException;



public class CalculadoraTest {
	
	private Calculadora calc;
	
	@BeforeEach
	public void setup(){
		calc = new Calculadora();
	}

	@Test
	public void deveSomarDoisValores(){
		//cenario
		int a = 5;
		int b = 3;
		
		//acao
		int resultado = calc.somar(a, b);
		
		//verificacao
		Assert.assertEquals(8, resultado);
		
	}
	
	@Test
	public void deveSubtrairDoisValores(){
		//cenario
		int a = 8;
		int b = 5;
		
		//acao
		int resultado = calc.subtrair(a, b);
		
		//verificacao
		Assert.assertEquals(3, resultado);
		
	}
	
	@Test
	public void deveDividirDoisValores() throws NaoPodeDividirPorZeroException{
		//cenario
		int a = 6;
		int b = 3;
		
		//acao
		int resultado = calc.divide(a, b);
		
		//verificacao
		Assert.assertEquals(2, resultado);
	}
	
	
}
