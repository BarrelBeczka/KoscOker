package com.example.koscoker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import java.util.Random;

// Własny widok - Stół do gry w kości
public class WidokStolu extends View {

    private Paint pedzelStolu;
    private Paint pedzelKosci;
    private Paint pedzelTekstu;
    private int[] wynikiKosci = {1, 2, 3, 4, 5}; // Przykładowe wyniki na start

    public WidokStolu(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inicjuj();
    }

    private void inicjuj() {
        // Pędzel do tła (zielony stół)
        pedzelStolu = new Paint();
        pedzelStolu.setColor(Color.parseColor("#2E7D32"));
        pedzelStolu.setStyle(Paint.Style.FILL);

        // Pędzel do kości (białe kwadraty)
        pedzelKosci = new Paint();
        pedzelKosci.setColor(Color.WHITE);
        pedzelKosci.setStyle(Paint.Style.FILL);

        // Pędzel do oczek/cyfr
        pedzelTekstu = new Paint();
        pedzelTekstu.setColor(Color.BLACK);
        pedzelTekstu.setTextSize(60f);
        pedzelTekstu.setTextAlign(Paint.Align.CENTER);
    }

    // Metoda wywoływana z wątku gry, aby zaktualizować wyniki
    public void aktualizujWyniki(int[] noweWyniki) {
        this.wynikiKosci = noweWyniki;
        postInvalidate(); // Odśwież widok (bezpieczne wywołanie z innego wątku)
    }

    @Override
    protected void onDraw(Canvas plotno) {
        super.onDraw(plotno);

        // 1. Rysowanie tła (stół)
        plotno.drawRect(0, 0, getWidth(), getHeight(), pedzelStolu);

        // 2. Rysowanie 5 kości
        float szerokosc = getWidth();
        float srodekY = getHeight() / 2f;
        float rozmiarKosci = 150f;
        float odstep = 20f;
        float startX = (szerokosc - (5 * rozmiarKosci + 4 * odstep)) / 2f;

        for (int i = 0; i < 5; i++) {
            float x = startX + i * (rozmiarKosci + odstep);
            float y = srodekY - rozmiarKosci / 2f;

            // Rysuj kwadrat kości
            plotno.drawRect(x, y, x + rozmiarKosci, y + rozmiarKosci, pedzelKosci);

            // Rysuj wynik (liczbę) na środku kości
            plotno.drawText(
                String.valueOf(wynikiKosci[i]),
                x + rozmiarKosci / 2f,
                y + rozmiarKosci / 1.5f,
                pedzelTekstu
            );
        }
    }
}
