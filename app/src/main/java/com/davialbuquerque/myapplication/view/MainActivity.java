package com.davialbuquerque.myapplication.view;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.View;

import com.davialbuquerque.myapplication.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupEpisodesFragment();
    }

    private void setupEpisodesFragment() {
        Fragment fragment = new EpisodesFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_layout, fragment).commitAllowingStateLoss();
    }



}
