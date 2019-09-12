package com.davialbuquerque.myapplication.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.davialbuquerque.myapplication.api.EpisodeRepo;
import com.davialbuquerque.myapplication.model.Episode;

import java.util.ArrayList;
import java.util.List;

public class EpisodeViewModel extends ViewModel {

    private MutableLiveData<List<Episode>> episodes;
    private MutableLiveData<Integer> status;

    public void init() {
        status = EpisodeRepo.getStatus();
        episodes = EpisodeRepo.getAllEpisodes();
    }

    public LiveData<List<Episode>> getEpisodes() {
        return episodes;
    }

    public LiveData<Integer> getStatus() {
        return status;
    }

}
