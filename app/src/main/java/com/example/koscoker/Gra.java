package com.example.koscoker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.koscoker.databinding.FragmentGraBinding;

// Fragment z rozgrywką
public class Gra extends Fragment {

    private FragmentGraBinding binding;
    private WatekGry watekGry;
    private BazaDanych db;
    private android.os.Handler handler = new android.os.Handler();

    // Początkowe wartości - stan gry
    private int turaGracza = 1; // 1 lub 2
    private int numerRzutu = 0; // 0, 1, 2
    private int punktyGracz1 = 0;
    private int punktyGracz2 = 0;

    public Gra() {
        // Pusty konstruktor
    }

    // Tworzenie widoku fragmentu
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGraBinding.inflate(inflater, container, false);
        db = new BazaDanych(getContext());

        aktualizujUI();
        
        binding.przyciskRzuc.setOnClickListener(v -> obsluzPrzycisk());

        // Nasłuchiwanie dotyku na stole, aby zaktualizować tekst przycisku
        binding.widokStolu.setOnClickListener(v -> aktualizujTekstPrzycisku());
        return binding.getRoot();
    }
    
    // Metoda pomocnicza do aktualizacji tekstu
    private void aktualizujTekstPrzycisku() {
    }

    // Obsługa kliknięcia przycisku "Rzuć Kośćmi"
    private void obsluzPrzycisk() {
        if (numerRzutu < 2) {
            // Sprawdź czy to drugi rzut i czy gracz nic nie zaznaczył
            // Jeśli gracz nic nie zaznaczył w 2. fazie, to znaczy że pasuje i kończymy turę.
            if (numerRzutu == 1 && !czyWybranoKosci()) {
                zakonczTure();
            } else {
                wykonajRzut(); // Normalny rzut
            }
        } else {
            // Jeśli to był już drugi rzut, przycisk służy do zakończenia tury
            zakonczTure();
        }
    }
    
    // Sprawdza, czy jakakolwiek kość jest zaznaczona
    private boolean czyWybranoKosci() {
        boolean[] wybrane = binding.widokStolu.getZablokowaneKosci();
        for (boolean b : wybrane) {
            if (b) return true;
        }
        return false;
    }

    // Rozpoczyna animację rzutu w nowym wątku
    private void wykonajRzut() {
        
        // Zabezpieczenie przed wielokrotnym kliknięciem w trakcie animacji
        if (watekGry != null && watekGry.isAlive()) {
            return;
        }
        
        binding.przyciskRzuc.setEnabled(false); // Blokada przycisku na czas rzutu

        // blokady dla wątku
        boolean[] blokadyDlaWatku = new boolean[5];
        if (numerRzutu > 0) {
            boolean[] wybrane = binding.widokStolu.getZablokowaneKosci();
            for(int i=0; i<5; i++) {
                // Jeśli kość JEST zaznaczona (wybrane[i]), to chcemy nią rzucić -> blokada = false
                // Jeśli kość NIE jest zaznaczona, to zostaje -> blokada = true
                blokadyDlaWatku[i] = !wybrane[i];
            }
        }
        // Jeśli Rzut 0 (start), blokadyDlaWatku to same false 

        // Utwórz i uruchom nowy wątek symulujący rzut
        // Przekazujemy obecny stan kości i blokady
        watekGry = new WatekGry(binding.widokStolu, binding.widokStolu.getWyniki(), blokadyDlaWatku);
        watekGry.start();

        // Handler oczekuje na zakończenie wątku żeby odblokować interfejs
        handler.postDelayed(() -> {
            numerRzutu++;
            binding.widokStolu.resetujBlokady(); // Zdejmij zaznaczenia z kości
            aktualizujUI(); // Odśwież napisy na przyciskach
            
            // Oblicz i wyświetl bieżący układ
            int ranga = LogikaPokera.sprawdzUklad(binding.widokStolu.getWyniki());
            binding.tekstWynikBiezacy.setText("Wynik: " + LogikaPokera.nazwaUkladu(ranga));
            
            binding.przyciskRzuc.setEnabled(true); // Odblokuj przycisk
        }, 2200);
    }
    
    // Kończy turę gracza i przechodzi do następnego lub kończy grę
    private void zakonczTure() {
        // Oblicz ostateczną rangę układu
        int ranga = LogikaPokera.sprawdzUklad(binding.widokStolu.getWyniki());

        if (turaGracza == 1) {
            // Koniec tury Gracza 1
            punktyGracz1 = ranga;
            turaGracza = 2; // Przełącz na Gracza 2
            numerRzutu = 0;
            binding.widokStolu.resetujBlokady();
            binding.widokStolu.aktualizujWyniki(new int[]{0,0,0,0,0}); // Wyczyść stół
            binding.tekstWynikBiezacy.setText(""); // Wyczyść tekst wyniku
            aktualizujUI();
        } else {
            // Koniec tury Gracza 2 -> Koniec Gry
            punktyGracz2 = ranga;
            zapiszWynikIPokazPodsumowanie();
        }
    }
    
    private void zapiszWynikIPokazPodsumowanie() {
        // Zapis do bazy
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm", java.util.Locale.getDefault());
        String data = sdf.format(new java.util.Date());
        db.dodajWynik(punktyGracz1, punktyGracz2, data);
        
        // Komunikat końcowy
        String nazwaG1 = LogikaPokera.nazwaUkladu(punktyGracz1);
        String nazwaG2 = LogikaPokera.nazwaUkladu(punktyGracz2);
        
        String zwyciezca;
        if (punktyGracz1 > punktyGracz2) {
            zwyciezca = "Gracz 1";
        } else if (punktyGracz2 > punktyGracz1) {
            zwyciezca = "Gracz 2";
        } else {
            zwyciezca = "Remis";
        }
        
        String message = "Gracz 1: " + nazwaG1 + "\nGracz 2: " + nazwaG2 + "\n\nWygrał: " + zwyciezca;
        
        new android.app.AlertDialog.Builder(getContext())
            .setTitle("Koniec Gry")
            .setMessage(message)
            .setPositiveButton("Menu", (d, w) -> {
                ((MainActivity) requireActivity()).zmienFragment(new Menu());
            })
            .setCancelable(false)
            .show();
    }

    // Funkcja aktualizująca interfejs
    private void aktualizujUI() {
        if (turaGracza <= 2) {
            binding.tekstTura.setText("Tura: Gracz " + turaGracza);
        }
        
        if (numerRzutu == 0) {
            binding.przyciskRzuc.setText("Rzuć Kośćmi");
        } else if (numerRzutu == 1) {
            binding.przyciskRzuc.setText("Rzuć zaznaczone (lub Dalej)");
        } else {
            binding.przyciskRzuc.setText(turaGracza == 1 ? "Przejdź do Gracza 2" : "Zakończ i Zapisz");
        }
    }

    // Funkcja zwalniająca zasoby zabija wątek gry i zamyka bazę danych
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (watekGry != null) {
            watekGry.zatrzymaj();
        }
        if (db != null) {
            db.close();
        }
        binding = null;
    }
}
