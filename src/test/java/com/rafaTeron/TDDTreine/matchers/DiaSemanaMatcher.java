package com.rafaTeron.TDDTreine.matchers;

import java.time.DayOfWeek;
import java.time.LocalDate;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import com.rafaTeron.TDDTreine.utils.DataUtils;

public class DiaSemanaMatcher extends TypeSafeMatcher<LocalDate> {

    private DayOfWeek diaSemana;

    public DiaSemanaMatcher(DayOfWeek diaSemana) {
        this.diaSemana = diaSemana;
    }

    public void describeTo(Description desc) {
    	desc.appendValue(diaSemana);
    }

    @Override
    protected boolean matchesSafely(LocalDate data) {
        return DataUtils.verificarDiaSemana(data, diaSemana);
    }
}
