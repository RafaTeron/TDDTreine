package com.rafaTeron.TDDTreine.matchers;

import java.time.LocalDate;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import com.rafaTeron.TDDTreine.utils.DataUtils;

public class DataDiferencaDiasMatcher extends TypeSafeMatcher<LocalDate> {

    private Integer qtdDias;

    public DataDiferencaDiasMatcher(Integer qtdDias) {
        this.qtdDias = qtdDias;
    }

    public void describeTo(Description description) {
    	
    }

    @Override
    protected boolean matchesSafely(LocalDate data) {
    	return DataUtils.isMesmaData(data, DataUtils.obterDataComDiferencaDias(qtdDias));
    }
}
