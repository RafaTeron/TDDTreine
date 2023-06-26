package com.rafaTeron.TDDTreine.matchers;

import java.time.LocalDate;
import java.util.Calendar;

public class MatchersProprios {

    public static DiaSemanaMatcher caiEm(Integer diaSemana) {
        return new DiaSemanaMatcher(diaSemana);
    }

    public static DiaSemanaMatcher caiNumaSegunda() {
        return new DiaSemanaMatcher(Calendar.MONDAY);
    }

    public static DataDiferencaDiasMatcher ehHojeComDiferencaDias(Integer qtdDias) {
        return new DataDiferencaDiasMatcher(qtdDias);
    }

    public static LocalDate hoje() {
        return LocalDate.now();
    }

}




