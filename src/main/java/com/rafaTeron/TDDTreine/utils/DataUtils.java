package com.rafaTeron.TDDTreine.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class DataUtils {
	
	
	 public static LocalDate adicionarDias(LocalDate data, int dias) {
	        return data.plusDays(dias);
	    }
	    
	    public static LocalDate obterDataComDiferencaDias(int dias) {
	        return LocalDate.now().plusDays(dias);
	    }
	    
	    public static LocalDate obterData(int dia, int mes, int ano) {
	        return LocalDate.of(ano, mes, dia);
	    }
	    
	    public static boolean isMesmaData(LocalDate data1, LocalDate data2) {
	        return data1.isEqual(data2);
	    }
	    
	    public static boolean verificarDiaSemana(LocalDate data, DayOfWeek diaSemana) {
	        return data.getDayOfWeek() == diaSemana;
	    }
	}

