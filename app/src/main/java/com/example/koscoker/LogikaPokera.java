package com.example.koscoker;

import java.util.Arrays;

public class LogikaPokera {

    // Stałe oznaczające rangę układu
    public static final int NIC = 1;
    public static final int PARA = 2;
    public static final int DWIE_PARY = 3;
    public static final int TROJKA = 4;
    public static final int MALY_STRIT = 5;
    public static final int DUZY_STRIT = 6;
    public static final int FULL = 7;
    public static final int KARETA = 8;
    public static final int POKER = 9;

    // Funkcja sprawdzająca układ kości
    public static int sprawdzUklad(int[] kosci) {
        if (kosci == null || kosci.length != 5) return NIC;

        // Klonowanie i sortowanie kości
        int[] posortowane = kosci.clone();
        Arrays.sort(posortowane);

        // Zliczanie wystąpień każdej cyfry
        int[] liczniki = new int[7]; 
        for (int k : posortowane) {
            if (k >= 1 && k <= 6) {
                liczniki[k]++;
            }
        }

        // Sprawdzanie od najsilniejszego układu

        // 1. POKER (5 takich samych kości)
        for (int i = 1; i <= 6; i++) {
            if (liczniki[i] == 5) return POKER;
        }

        // 2. KARETA (4 takie same kości)
        for (int i = 1; i <= 6; i++) {
            if (liczniki[i] == 4) return KARETA;
        }

        // 3. FULL (3 takie same + 2 takie same)
        boolean trojka = false;
        boolean para = false;
        for (int i = 1; i <= 6; i++) {
            if (liczniki[i] == 3) trojka = true;
            if (liczniki[i] == 2) para = true;
        }
        if (trojka && para) return FULL;

        // 4. DUŻY STRIT (sekwencja 2, 3, 4, 5, 6)
        if (liczniki[2]==1 && liczniki[3]==1 && liczniki[4]==1 && liczniki[5]==1 && liczniki[6]==1) {
            return DUZY_STRIT;
        }

        // 5. MAŁY STRIT (sekwencja 1, 2, 3, 4, 5)
        if (liczniki[1]==1 && liczniki[2]==1 && liczniki[3]==1 && liczniki[4]==1 && liczniki[5]==1) {
            return MALY_STRIT;
        }

        // 6. TRÓJKA (3 takie same)
        if (trojka) return TROJKA; 

        // 7. DWIE PARY (2 + 2)
        int liczbaPar = 0;
        for (int i = 1; i <= 6; i++) {
            if (liczniki[i] == 2) liczbaPar++;
        }
        if (liczbaPar == 2) return DWIE_PARY;

        // 8. PARA (2 takie same)
        if (liczbaPar == 1) return PARA;

        // 9. NIC 
        return NIC;
    }

    // Funkcja zwracająca nazwę układu na podstawie rangi.
    public static String nazwaUkladu(int ranga) {
        switch (ranga) {
            case POKER: return "Poker";
            case KARETA: return "Kareta";
            case FULL: return "Full";
            case DUZY_STRIT: return "Duży Strit";
            case MALY_STRIT: return "Mały Strit";
            case TROJKA: return "Trójka";
            case DWIE_PARY: return "Dwie Pary";
            case PARA: return "Para";
            default: return "Nic";
        }
    }
}
