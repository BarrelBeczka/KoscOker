package com.example.koscoker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.koscoker.databinding.FragmentMenuBinding;

// Fragment wyświetlający menu główne z przyciskiem Graj
public class MenuFragment extends Fragment {

    private FragmentMenuBinding binding;

    public MenuFragment() {
        // Pusty konstruktor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMenuBinding.inflate(inflater, container, false);

        // Obsługa przycisku "Graj" - przejście do ekranu gry
        binding.przyciskGraj.setOnClickListener(v -> {
            ((MainActivity) requireActivity()).zmienFragment(new GraFragment());
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
