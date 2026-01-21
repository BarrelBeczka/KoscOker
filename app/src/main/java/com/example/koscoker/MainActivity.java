package com.example.koscoker;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.example.koscoker.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Na start ładujemy menu
        if (savedInstanceState == null) {
            zmienFragment(new MenuFragment());
        }
    }

    // Metoda publiczna, dostępna dla fragmentów do nawigacji
    public void zmienFragment(Fragment fragment) {
        FragmentTransaction transakcja = getSupportFragmentManager().beginTransaction();
        transakcja.replace(R.id.kontener_fragmentow, fragment);
        transakcja.addToBackStack(null); // Pozwala cofnąć się przyciskiem "Wstecz"
        transakcja.commit();
    }
}