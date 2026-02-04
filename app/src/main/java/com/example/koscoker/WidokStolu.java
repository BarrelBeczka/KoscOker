package com.example.koscoker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import java.util.Random;

// Widok stołu do gry
public class WidokStolu extends View {

    private Paint pedzelStolu;
    private Paint pedzelKosci;
    private Paint pedzelTekstu;
    private int[] wynikiKosci = {0, 0, 0, 0, 0}; // Puste na start

    // Konstruktor widoku stołu
    public WidokStolu(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inicjuj();
    }

    // Tablica przechowująca stan zablokowania kości
    private boolean[] zablokowaneKosci = new boolean[5];
    private Paint pedzelZablokowany;

    // Metoda inicjująca pędzle i kolory
    public void inicjuj() {
        // Pędzel do tła (zielony stół)
        pedzelStolu = new Paint();
        pedzelStolu.setColor(Color.parseColor("#2E7D32"));
        pedzelStolu.setStyle(Paint.Style.FILL);

        // Pędzel do kości (białe kwadraty)
        pedzelKosci = new Paint();
        pedzelKosci.setColor(Color.WHITE);
        pedzelKosci.setStyle(Paint.Style.FILL);

        // Pędzel do zablokowanych kości (szary)
        pedzelZablokowany = new Paint();
        pedzelZablokowany.setColor(Color.LTGRAY);
        pedzelZablokowany.setStyle(Paint.Style.FILL);

        // Pędzel do oczek/cyfr
        pedzelTekstu = new Paint();
        pedzelTekstu.setColor(Color.BLACK);
        pedzelTekstu.setTextSize(60f);
        pedzelTekstu.setTextAlign(Paint.Align.CENTER);
    }

    // Metoda wywoływana z wątku gry, aby zaktualizować wyniki
    public void aktualizujWyniki(int[] noweWyniki) {
        this.wynikiKosci = noweWyniki;
        postInvalidate(); // Odśwież widok
    }

    // Metoda zwracająca wyniki kości
    public int[] getWyniki() {
        return wynikiKosci;
    }

    // Metoda zwracająca zablokowane kości
    public boolean[] getZablokowaneKosci() {
        return zablokowaneKosci;
    }

    // Metoda resetująca blokady kości
    public void resetujBlokady() {
        for (int i = 0; i < 5; i++) {
            zablokowaneKosci[i] = false;
        }
        invalidate();
    }

    // Metoda wywoływana przy kliknięciu w ekran
    @Override
    public boolean onTouchEvent(android.view.MotionEvent event) {
        if (event.getAction() == android.view.MotionEvent.ACTION_DOWN) {
            float x = event.getX();
            float y = event.getY();

            // Obliczanie pozycji kości
            float szerokosc = getWidth();
            float srodekY = getHeight() / 2f;
            float rozmiarKosci = 150f;
            float odstep = 20f;
            float startX = (szerokosc - (5 * rozmiarKosci + 4 * odstep)) / 2f;

            // Sprawdzamy czy kliknięto w którąś z kości
            for (int i = 0; i < 5; i++) {
                float koscX = startX + i * (rozmiarKosci + odstep);
                float koscY = srodekY - rozmiarKosci / 2f;

                // Sprawdzenie czy dotknięto kości
                if (x >= koscX && x <= koscX + rozmiarKosci &&
                    y >= koscY && y <= koscY + rozmiarKosci) {
                    
                    // Blokada zaznaczenia jeśli kość jest pusta
                    if (wynikiKosci[i] > 0) {
                        zablokowaneKosci[i] = !zablokowaneKosci[i]; // Zmień stan (Zaznaczona/Odznaczona)
                        invalidate(); // Wymuś odświeżenie widoku (uruchomi ponowne onDraw)
                    }
                    return true;
                }
            }
        }
        return super.onTouchEvent(event);
    }

    // Metoda rysująca kości na ekranie
    @Override
    protected void onDraw(Canvas plotno) {
        super.onDraw(plotno);

        // Rysowanie tła (zielony stół)
        plotno.drawRect(0, 0, getWidth(), getHeight(), pedzelStolu);

        // Parametry do rysowania kości na środku ekranu
        float szerokosc = getWidth();
        float srodekY = getHeight() / 2f;
        float rozmiarKosci = 150f;
        float odstep = 20f;
        
        // Obliczanie pozycji startowej pierwszej kości, aby całość była wycentrowana
        float startX = (szerokosc - (5 * rozmiarKosci + 4 * odstep)) / 2f;

        // Pętla rysująca 5 kości
        for (int i = 0; i < 5; i++) {
            float x = startX + i * (rozmiarKosci + odstep);
            float y = srodekY - rozmiarKosci / 2f;

            // Rysowanie kwadratu kości. Biały - normalna, Szary - zablokowana do przerzutu
            if (zablokowaneKosci[i]) {
                plotno.drawRect(x, y, x + rozmiarKosci, y + rozmiarKosci, pedzelZablokowany);
            } else {
                plotno.drawRect(x, y, x + rozmiarKosci, y + rozmiarKosci, pedzelKosci);
            }

            // Rysowanie liczb na środku kości jeżeli kość nie jest pusta
            if (wynikiKosci[i] > 0) {
                plotno.drawText(
                    String.valueOf(wynikiKosci[i]),
                    x + rozmiarKosci / 2f,
                    y + rozmiarKosci / 1.5f,
                    pedzelTekstu
                );
            }
        }
    }
}
