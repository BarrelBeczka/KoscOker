package com.example.koscoker;

// Klasa przechowująca pojedynczy wynik gry
public class Wynik {
    private int id;
    private int punktyGracz1;
    private int punktyGracz2;
    private String data;

    // Konstruktor wyniku
    public Wynik(int id, int punktyGracz1, int punktyGracz2, String data) {
        this.id = id;
        this.punktyGracz1 = punktyGracz1;
        this.punktyGracz2 = punktyGracz2;
        this.data = data;
    }

    // Metoda zwracająca id wyniku
    public int getId() {
        return id;
    }

    // Metoda zwracająca punkty gracza 1
    public int getPunktyGracz1() {
        return punktyGracz1;
    }

    // Metoda zwracająca punkty gracza 2
    public int getPunktyGracz2() {
        return punktyGracz2;
    }

    // Metoda zwracająca datę wyniku
    public String getData() {
        return data;
    }
}
