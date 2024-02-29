package com.example.proyectotatto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class ActivityMenu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new registradoFragmentCartas()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }

        // Inflar el layout de usuarios registrados
        LayoutInflater inflater = LayoutInflater.from(this);
        View usuariosRegistradosLayout = inflater.inflate(R.layout.activity_usuarios_registrados, drawerLayout, false);
        drawerLayout.addView(usuariosRegistradosLayout);

        UsuariosRegistrados usuariosRegistrados = new UsuariosRegistrados();
        usuariosRegistrados.setOnMenuButtonClickListener(new UsuariosRegistrados.OnMenuButtonClickListener() {
            @Override
            public void onMenuButtonClicked() {
                Log.d("ActivityMenu", "onMenuButtonClicked");
            }
        });

        // Obtener referencia al ImageButton desde el layout inflado
        ImageButton imageButton = usuariosRegistradosLayout.findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Llamar al método onMenuButtonClicked cuando se hace clic en la imagen
                onMenuButtonClicked();
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemID = item.getItemId();

        if (itemID == R.id.nav_home) {
            replaceFragment(new homeFragment());
        } else if (itemID == R.id.nav_settings) {
            replaceFragment(new settingsFragment());
        } else if (itemID == R.id.nav_info) {
            replaceFragment(new infoFragment());
        } else if (itemID == R.id.nav_logout) {
            replaceFragment(new logoutFragment());
        }
        return true;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameL, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void onMenuButtonClicked() {
        if (!drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.openDrawer(GravityCompat.START);
        } else {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }
}