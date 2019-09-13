package com.davialbuquerque.myapplication.view;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.davialbuquerque.myapplication.R;
import com.davialbuquerque.myapplication.api.EpisodeRepo;
import com.davialbuquerque.myapplication.model.Episode;
import com.davialbuquerque.myapplication.viewmodel.RickAndMorTyViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class EpisodesFragment extends Fragment {

//    private EpisodeViewModel viewModel;
    private RickAndMorTyViewModel<Episode> viewModel;
    private RecyclerView recyclerView;
    private ListAdapter adapter;
    private SwipeRefreshLayout swipe;

    public EpisodesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_episodes, container, false);
//        viewModel = ViewModelProviders.of(this).get(EpisodeViewModel.class);
        viewModel = ViewModelProviders.of(this).get(RickAndMorTyViewModel.class);
        viewModel.init(EpisodeRepo.getAllEpisodes(), EpisodeRepo.getStatus());
        viewModel.getData().observe(this, new Observer<List<Episode>>() {
            @Override
            public void onChanged(List<Episode> episodes) {
                ((EpisodeAdapter)adapter).setData(episodes);
            }
        });
        viewModel.getStatus().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                swipe.setRefreshing(integer == EpisodeRepo.LOADING);
                if (integer == EpisodeRepo.FAILURE) {
                    Toast.makeText(getContext(), R.string.something_went_wrong, Toast.LENGTH_LONG).show();
                }
            }
        });
        EpisodeRepo.loadEpisodes();
        swipe = v.findViewById(R.id.swipe);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                EpisodeRepo.loadEpisodes();
            }
        });

        recyclerView = v.findViewById(R.id.recyclerView);
        adapter = new EpisodeAdapter();
        ((EpisodeAdapter)adapter).setEpisodeClickListener((MainActivity)getActivity());
        recyclerView.setAdapter(adapter);

        return v;
    }

}
