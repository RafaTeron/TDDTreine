package com.rafaTeron.TDDTreine.matchers;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import com.rafaTeron.TDDTreine.utils.DataUtils;

public class DiaSemanaMatcher extends TypeSafeMatcher<LocalDate> {

    private Integer diaSemana;

    public DiaSemanaMatcher(Integer diaSemana) {
        this.diaSemana = diaSemana;
    }

    public void describeTo(Description desc) {
    	DayOfWeek diaSemana = DayOfWeek.MONDAY;
    	LocalDate data = LocalDate.now().with(diaSemana);
    	String dataExtenso = data.getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("pt", "BR"));
    	desc.appendText(dataExtenso);
    }

    @Override
    protected boolean matchesSafely(LocalDate data) {
        return DataUtils.verificarDiaSemana(data, diaSemana);
    }
}
