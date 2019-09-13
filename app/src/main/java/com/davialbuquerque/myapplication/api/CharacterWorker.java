package com.davialbuquerque.myapplication.api;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.davialbuquerque.myapplication.model.Character;
import com.davialbuquerque.myapplication.model.EpisodeResponse;

import org.json.JSONObject;

public class CharacterWorker extends Worker {
    private static final String DEAD_IDS = "DEAD_IDS";

    public CharacterWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        CharacterRepo.backedCharacters.clear();
        CharacterRepo.status.postValue(CharacterRepo.LOADING);
        String[] urls = getInputData().getStringArray(CharacterRepo.CHAR_URLS);
        if (urls != null) {
            for (String url : urls) {
                JSONObject json = ConnectionHelper.loadJson(url);

                if (json != null) {
                    Character character = Character.fromJson(json, getApplicationContext().getSharedPreferences(DEAD_IDS, Context.MODE_PRIVATE));
                    if (character != null) {
                        Log.d("TAG", "Character: "+character);
                        CharacterRepo.backedCharacters.add(character);
                        CharacterRepo.characters.postValue(CharacterRepo.backedCharacters);
                    }
                } else {
                    EpisodeRepo.status.postValue(EpisodeRepo.FAILURE);
                }
            }
        }
        return Result.success();
    }
}
