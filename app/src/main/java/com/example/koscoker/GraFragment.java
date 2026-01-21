package com.example.koscoker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.koscoker.databinding.FragmentGraBinding;

// Fragment z właściwą rozgrywką
public class GraFragment extends Fragment {

    private FragmentGraBinding binding;
    private WatekGry watekGry;

    public GraFragment() {
        // Pusty konstruktor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGraBinding.inflate(inflater, container, false);

        binding.przyciskRzuc.setOnClickListener(v -> rozpocznijRzut());

        return binding.getRoot();
    }

    private void rozpocznijRzut() {
        // Zabezpieczenie przed wielokrotnym kliknięciem
        if (watekGry != null && watekGry.isAlive()) {
            return; 
        }

        // Utwórz i uruchom nowy wątek
        watekGry = new WatekGry(binding.widokStolu);
        watekGry.start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Zatrzymaj wątek jeśli wychodzimy z ekranu
        if (watekGry != null) {
            watekGry.zatrzymaj();
        }
        binding = null;
    }
}
