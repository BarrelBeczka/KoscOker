package com.example.koscoker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.koscoker.databinding.FragmentMenuBinding;

// Fragment wyświetlający menu główne z przyciskiem Graj
public class Menu extends Fragment {

    private FragmentMenuBinding binding;

    public Menu() {
        // Pusty konstruktor
    }
    
    // Metoda tworząca widok menu głównego
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMenuBinding.inflate(inflater, container, false);

        // Obsługa przycisku "Graj" - przejście do ekranu gry
        binding.przyciskGraj.setOnClickListener(v -> {
            ((MainActivity) requireActivity()).zmienFragment(new Gra());
        });

        // Obsługa przycisku "Wyniki"
        binding.przyciskWyniki.setOnClickListener(v -> {
            ((MainActivity) requireActivity()).zmienFragment(new Wyniki());
        });

        return binding.getRoot();
    }
    
    // Metoda czyszcząca widok menu głównego
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
