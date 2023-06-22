package com.rafaTeron.TDDTreine.matchers;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class DiaSemanaMatcher extends TypeSafeMatcher<LocalDate> {

    private DayOfWeek diaSemana;

    public DiaSemanaMatcher(DayOfWeek diaSemana) {
        this.diaSemana = diaSemana;
    }

    public void describeTo(Description desc) {
        String dataExtenso = diaSemana.getDisplayName(TextStyle.FULL, new Locale("pt", "BR"));
        desc.appendText(dataExtenso);
    }

    @Override
    protected boolean matchesSafely(LocalDate data) {
        return data.getDayOfWeek() == diaSemana;
    }
}
