package com.davialbuquerque.myapplication.api;

import androidx.lifecycle.MutableLiveData;
import androidx.work.Constraints;
import androidx.work.ExistingWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.davialbuquerque.myapplication.model.Episode;

import java.util.ArrayList;
import java.util.List;

public class EpisodeRepo {

    private static final String TAG = "getCharacters";
    static MutableLiveData<List<Episode>> episodes = new MutableLiveData<>();
    static List<Episode> backedEpisodes = new ArrayList<>();

    public static int STAND_BY = 0;
    public static int LOADING = 1;
    public static int SUCCESS = 2;
    public static int FAILURE = -1;


    static MutableLiveData<Integer> status = new MutableLiveData<>();

    public static MutableLiveData<List<Episode>> getAllEpisodes() {
        return episodes;
    }

    public static void loadEpisodes() {
        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(EpisodeWorker.class)
                .setConstraints(new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build())
                .build();

        WorkManager.getInstance().beginUniqueWork(TAG, ExistingWorkPolicy.KEEP, request).enqueue();
    }

    public static MutableLiveData<Integer> getStatus() {
        return status;
    }
}