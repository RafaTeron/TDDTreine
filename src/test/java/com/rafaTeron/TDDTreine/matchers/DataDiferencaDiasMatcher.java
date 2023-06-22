package com.rafaTeron.TDDTreine.matchers;

import java.time.LocalDate;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import com.rafaTeron.TDDTreine.utils.DataUtils;

public class DataDiferencaDiasMatcher extends TypeSafeMatcher<LocalDate> {

    private int qtdDias;

    public DataDiferencaDiasMatcher(int qtdDias) {
        this.qtdDias = qtdDias;
    }

    public void describeTo(Description description) {
    	
    }

    @Override
    protected boolean matchesSafely(LocalDate data) {
        LocalDate dataComparacao = DataUtils.obterDataComDiferencaDias(qtdDias);
        return DataUtils.isMesmaData(data, dataComparacao);
    }
}
