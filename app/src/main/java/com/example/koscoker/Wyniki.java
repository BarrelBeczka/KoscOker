package com.example.koscoker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.koscoker.databinding.FragmentWynikiBinding;
import java.util.List;

public class Wyniki extends Fragment {

    private FragmentWynikiBinding binding;
    private BazaDanych db;

    public Wyniki() {
        // Pusty konstruktor publiczny
    }

    // Metoda tworząca widok fragmentu
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWynikiBinding.inflate(inflater, container, false);
        
        // Inicjalizacja obiektu db do obsługi bazy danych
        db = new BazaDanych(getContext());
        
        // Pobranie listy wszystkich wyników z tabeli
        List<Wynik> listaWynikow = db.pobierzWszystkieWyniki();
        
        // Konfiguracja RecyclerView i LinearLayoutManager (lista pionowa)
        binding.listaWynikow.setLayoutManager(new LinearLayoutManager(getContext()));
        
        // Adapter do wyświetlania wyników
        zaladujWyniki();

        // Obsługa przycisku powrotu do Menu
        binding.przyciskWroc.setOnClickListener(v -> {
            ((MainActivity) requireActivity()).zmienFragment(new Menu());
        });

        return binding.getRoot();
    }
    
    // Metoda do ładowania wyników
    private void zaladujWyniki() {
        List<Wynik> wyniki = db.pobierzWszystkieWyniki();
        MojAdapterWynikow adapter = new MojAdapterWynikow(wyniki);
        binding.listaWynikow.setAdapter(adapter);
    }

    // Metoda niszcząca widok fragmentu
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        if (db != null) {
            db.close();
        }
    }
}
