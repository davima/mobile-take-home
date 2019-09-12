package com.davialbuquerque.myapplication.api;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.davialbuquerque.myapplication.model.EpisodeResponse;

import org.json.JSONObject;

public class EpisodeWorker extends Worker {
    private static final String url = "https://rickandmortyapi.com/api/episode";

    public EpisodeWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        EpisodeRepo.backedEpisodes.clear();
        boolean run = true;
        EpisodeRepo.status.postValue(EpisodeRepo.LOADING);
        String currentUrl = url;
        while(run) {
            JSONObject json = ConnectionHelper.loadJson(currentUrl);

            if (json != null) {
                EpisodeResponse episodeResponse = EpisodeResponse.parseEpisodeResponse(json);
                EpisodeRepo.backedEpisodes.addAll(episodeResponse.getEpisodes());
                EpisodeRepo.episodes.postValue(EpisodeRepo.backedEpisodes);
                currentUrl = episodeResponse.getInfo().getNext();
                if (episodeResponse.getInfo().getNext().isEmpty()) {
                    run = false;
                    EpisodeRepo.status.postValue(EpisodeRepo.SUCCESS);
                }
            } else {
                run = false;
                EpisodeRepo.status.postValue(EpisodeRepo.FAILURE);
            }
        }

        return Result.success();
    }
}
