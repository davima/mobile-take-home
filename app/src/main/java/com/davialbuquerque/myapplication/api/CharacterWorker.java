package com.davialbuquerque.myapplication.api;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.davialbuquerque.myapplication.model.Character;
import com.davialbuquerque.myapplication.model.EpisodeResponse;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;

public class CharacterWorker extends Worker {
    public static final String DEAD_IDS = "DEAD_IDS";

    public CharacterWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        String[] characterUrls = getInputData().getStringArray(CharacterRepo.CHAR_URLS);
        String imageUrl = getInputData().getString(CharacterRepo.IMAGE_URL);
        if (characterUrls != null) {
            loadCharacters(characterUrls);
        } else if (imageUrl != null){
            loadImage(imageUrl);
        }
        return Result.success();
    }

    private void loadCharacters(String[] urls) {
        CharacterRepo.backedCharacters.clear();
        CharacterRepo.status.postValue(CharacterRepo.LOADING);

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
    }

    private void loadImage(String url) {
        Bitmap bmp = null;
        InputStream inputStream = null;
        try {
            inputStream = new URL(url).openStream();
            bmp = BitmapFactory.decodeStream(inputStream);
            inputStream.close();

            CharacterRepo.characterImage.postValue(bmp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
