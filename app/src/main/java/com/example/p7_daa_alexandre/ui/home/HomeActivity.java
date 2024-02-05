package com.example.p7_daa_alexandre.ui.home;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.p7_daa_alexandre.MainActivity;
import com.example.p7_daa_alexandre.R;
import com.example.p7_daa_alexandre.ViewModelFactory;
import com.example.p7_daa_alexandre.databinding.ActivityHomeBinding;
import com.example.p7_daa_alexandre.ui.coworker.CoworkerFragment;
import com.example.p7_daa_alexandre.ui.details.DetailsViewModel;
import com.example.p7_daa_alexandre.ui.list.ListFragment;
import com.example.p7_daa_alexandre.ui.map.MapFragment;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding binding;

    HomeViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(HomeViewModel.class);
        replaceFragment(new MapFragment());
        setSupportActionBar(binding.toolbar);

        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.map:
                    replaceFragment(new MapFragment());
                    break;
                case R.id.list:
                    replaceFragment(new ListFragment());
                    break;
                case R.id.coworker:
                    replaceFragment(new CoworkerFragment());
                    break;
            }
            return true;
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_restaurant:
                // Action pour filtrer par jour
                SearchRestaurant();
                return true;

            case R.id.btn_logout:
                LogOut();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    private void SearchRestaurant() {

    }

    private void LogOut() {
        viewModel.signOut(this).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // La déconnexion a réussi, vous pouvez maintenant rediriger l'utilisateur vers l'écran de connexion ou effectuer d'autres actions nécessaires.
                // Exemple : Redirection vers l'écran de connexion
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Facultatif, selon vos besoins
            } else {
                // La déconnexion a échoué, gérer l'erreur ici si nécessaire
                Toast.makeText(HomeActivity.this, "Erreur lors de la déconnexion", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void replaceFragment(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_fragment, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}