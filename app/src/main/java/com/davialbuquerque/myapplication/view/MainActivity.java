package com.davialbuquerque.myapplication.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.davialbuquerque.myapplication.R;
import com.davialbuquerque.myapplication.model.Character;
import com.davialbuquerque.myapplication.model.Episode;

public class MainActivity extends AppCompatActivity implements ItemClickListener {

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

    @Override
    public void onEpisodeClick(Episode episode) {
        Fragment fragment = new EpisodeDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(EpisodeDetailFragment.EPISODE, episode);
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().addToBackStack(episode.getName()).add(R.id.fragment_layout, fragment, EpisodeDetailFragment.TAG).commitAllowingStateLoss();
    }

    @Override
    public void onCharacterClick(Character character) {
        CharacterDetailDialog dialog = new CharacterDetailDialog();
        Bundle bundle = new Bundle();
        bundle.putSerializable(CharacterDetailDialog.CHARACTER, character);
        dialog.setArguments(bundle);
        dialog.show(getSupportFragmentManager(), null);
    }


}
