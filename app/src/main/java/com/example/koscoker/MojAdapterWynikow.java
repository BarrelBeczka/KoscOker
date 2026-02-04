package com.example.koscoker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.koscoker.databinding.WierszWynikuBinding;
import java.util.List;

public class MojAdapterWynikow extends RecyclerView.Adapter<MojAdapterWynikow.WynikViewHolder> {
    // Klasa przechowująca widok pojedynczego wiersza w liście wyników
    private List<Wynik> listaWynikow;
    
    // Konstruktor adaptera
    public MojAdapterWynikow(List<Wynik> listaWynikow) {
        this.listaWynikow = listaWynikow;
    }

    // Metoda wywoływana, gdy RecyclerView potrzebuje nowego wiersza tworzy pusty szablon wiersza z pliku XML
    @NonNull
    @Override
    public WynikViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        WierszWynikuBinding binding = WierszWynikuBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new WynikViewHolder(binding);
    }

    // Metoda wywoływana, gdy trzeba uzupełnić wiersz konkretnymi danymi dla danego wiersza
    @Override
    public void onBindViewHolder(@NonNull WynikViewHolder holder, int position) {
        Wynik wynik = listaWynikow.get(position); // Pobierz wynik z listy dla tej pozycji
        holder.bind(wynik); // Wywołaj metodę uzupełniającą widok
    }

    // Metoda zwracająca liczbę wierszy w liście
    @Override
    public int getItemCount() {
        return listaWynikow.size();
    }

    // Klasa przechowująca widok pojedynczego wiersza w liście wyników
    static class WynikViewHolder extends RecyclerView.ViewHolder {

        private final WierszWynikuBinding binding;

        public WynikViewHolder(WierszWynikuBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        // Metoda uzupełniająca widok konkretnymi danymi dla danego wiersza
        public void bind(Wynik wynik) {
            binding.tekstData.setText("Data: " + wynik.getData());
            
            String nazwaG1 = LogikaPokera.nazwaUkladu(wynik.getPunktyGracz1());
            String nazwaG2 = LogikaPokera.nazwaUkladu(wynik.getPunktyGracz2());
            
            binding.tekstWynikG1.setText("Gracz 1: " + nazwaG1);
            binding.tekstWynikG2.setText("Gracz 2: " + nazwaG2);
        }
    }
}
