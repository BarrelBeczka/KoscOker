package com.example.koscoker;

import java.util.Random;

// Wątek odpowiedzialny za animację rzutu kośćmi
public class WatekGry extends Thread {

    // Zmienne przechowujące stan wątku
    private boolean czyDziala = true;
    private WidokStolu widokStolu;
    private Random losowy = new Random();
    private int[] obecneWyniki;
    private boolean[] zablokowane;

    // Konstruktor wątku gry
    public WatekGry(WidokStolu widok, int[] wyniki, boolean[] blokady) {
        this.widokStolu = widok;
        this.obecneWyniki = wyniki.clone(); // Kopia, żeby nie zmieniać oryginału 
        this.zablokowane = blokady != null ? blokady : new boolean[5];
    }

    // Metoda zatrzymująca wątek
    public void zatrzymaj() {
        czyDziala = false;
    }

    // Główna pętla wątku w której dzieje się animacja rzutu kośćmi
    @Override
    public void run() {
        int licznikRzutow = 0;
        
        // Pętla wykonuje się dopóki wątek jest aktywny (czyDziala) i nie przekroczono limitu rzutów (animacja)
        while (czyDziala && licznikRzutow < 20) { 
            try {
                // Uśpienie wątku na 100ms - symuluje opóźnienie i daje efekt animacji
                Thread.sleep(100); 
                
                // Klonujemy obecne wyniki, żeby pracować na kopii
                int[] noweWyniki = obecneWyniki.clone();
                
                // Losujemy nowe wartości dla każdej kości
                for (int i = 0; i < 5; i++) {
                    // Jeśli kość NIE jest zablokowana (zablokowane[i] == false), to losujemy nową wartość
                    if (!zablokowane[i]) {
                        noweWyniki[i] = losowy.nextInt(6) + 1; // Losuj liczbę 1-6
                    }
                }

                // Aktualizujemy widok na ekranie i sprawdzamy czy widokStolu nadal istnieje
                if (widokStolu != null) {
                    widokStolu.aktualizujWyniki(noweWyniki);
                }

                licznikRzutow++;
                
                // Aktualizacja obecnych wyników
                obecneWyniki = noweWyniki;

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // Po wyjściu z pętli wątek się kończy
    }
}
