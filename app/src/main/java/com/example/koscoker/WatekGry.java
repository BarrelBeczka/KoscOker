package com.example.koscoker;

import java.util.Random;

// Wątek odpowiedzialny za logikę gry (animację rzutu kośćmi)
public class WatekGry extends Thread {

    private boolean czyDziala = true;
    private WidokStolu widokStolu;
    private Random losowy = new Random();

    public WatekGry(WidokStolu widok) {
        this.widokStolu = widok;
    }

    // Metoda zatrzymująca wątek
    public void zatrzymaj() {
        czyDziala = false;
    }

    @Override
    public void run() {
        int licznikRzutow = 0;
        
        while (czyDziala && licznikRzutow < 20) { // Symulacja trwa np. 20 klatek (ok. 2 sekundy)
            try {
                // Odczekaj chwilę (symulacja myślenia/animacji)
                Thread.sleep(100); 
                
                // Wylosuj nowe wyniki
                int[] noweWyniki = new int[5];
                for (int i = 0; i < 5; i++) {
                    noweWyniki[i] = losowy.nextInt(6) + 1; // 1-6
                }

                // Zaktualizuj widok (to wywoła postInvalidate w widoku)
                if (widokStolu != null) {
                    widokStolu.aktualizujWyniki(noweWyniki);
                }

                licznikRzutow++;

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
