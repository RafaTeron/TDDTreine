package com.rafaTeron.TDDTreine.matchers;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class MatchersProprios {

    public static DiaSemanaMatcher caiEm(DayOfWeek diaSemana) {
        return new DiaSemanaMatcher(diaSemana);
    }

    public static DiaSemanaMatcher caiNumaSegunda() {
        return new DiaSemanaMatcher(DayOfWeek.MONDAY);
    }

    public static DataDiferencaDiasMatcher ehHojeComDiferencaDias(int qtdDias) {
        return new DataDiferencaDiasMatcher(qtdDias);
    }

    public static LocalDate hoje() {
        return LocalDate.now();
    }

}




