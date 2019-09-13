package com.davialbuquerque.myapplication.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.davialbuquerque.myapplication.R;
import com.davialbuquerque.myapplication.api.CharacterRepo;
import com.davialbuquerque.myapplication.api.EpisodeRepo;
import com.davialbuquerque.myapplication.model.Character;
import com.davialbuquerque.myapplication.model.Episode;
import com.davialbuquerque.myapplication.viewmodel.RickAndMorTyViewModel;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class EpisodeDetailFragment extends Fragment {

    static final String EPISODE = "EPISODE";
    static final String TAG = "EpisodeDetailFragment";
    static final String DEAD_IDS = "DEAD_CHARS";
    private Episode episode;

    private RickAndMorTyViewModel<Character> viewModel;
    private RecyclerView aliveRecyclerView;
    private RecyclerView deadRecyclerView;
    private ListAdapter aliveAdapter;
    private ListAdapter deadAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.episode_details_fragment, container, false);
        if (getArguments() != null)
            episode = (Episode) getArguments().getSerializable(EPISODE);

        ((TextView)view.findViewById(R.id.episode_title)).setText(episode.getName());
        aliveRecyclerView = view.findViewById(R.id.living_characters);
        deadRecyclerView = view.findViewById(R.id.dead_characters);

        viewModel = ViewModelProviders.of(this).get(RickAndMorTyViewModel.class);
        viewModel.init(CharacterRepo.getCharacters(), CharacterRepo.getStatus());
        viewModel.getData().observe(this, new Observer<List<Character>>() {
            @Override
            public void onChanged(List<Character> characters) {
                List<Character> alive = characters.stream().filter(new Predicate<Character>() {
                    @Override
                    public boolean test(Character character) {
                        return character.getStatus().equalsIgnoreCase("alive");
                    }
                }).collect(Collectors.<Character>toList());

                List<Character> dead = characters.stream().filter(new Predicate<Character>() {
                    @Override
                    public boolean test(Character character) {
                        return character.getStatus().equalsIgnoreCase("dead");
                    }
                }).collect(Collectors.<Character>toList());
                ((CharacterAdapter) aliveAdapter).setData(alive);
                ((CharacterAdapter) deadAdapter).setData(dead);
            }
        });
        viewModel.getStatus().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer == EpisodeRepo.FAILURE) {
                    Toast.makeText(getContext(), R.string.something_went_wrong, Toast.LENGTH_LONG).show();
                }
            }
        });
        CharacterRepo.loadCharacters(episode.getCharacters());

        aliveAdapter = new CharacterAdapter();
        ((CharacterAdapter) aliveAdapter).setEpisodeClickListener((MainActivity) getActivity());
        aliveRecyclerView.setAdapter(aliveAdapter);

        deadAdapter = new CharacterAdapter();
        ((CharacterAdapter) deadAdapter).setEpisodeClickListener((MainActivity) getActivity());
        deadRecyclerView.setAdapter(deadAdapter);

        return view;
    }


}
