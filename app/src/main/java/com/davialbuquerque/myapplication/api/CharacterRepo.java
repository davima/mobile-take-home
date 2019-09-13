package com.davialbuquerque.myapplication.api;

import android.graphics.Bitmap;

import androidx.lifecycle.MutableLiveData;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.ExistingWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.davialbuquerque.myapplication.model.Character;

import java.util.ArrayList;
import java.util.List;

public class CharacterRepo {

    private static final String TAG = "getEpisodeCharacters";
    static MutableLiveData<List<Character>> characters = new MutableLiveData<>();
    static List<Character> backedCharacters = new ArrayList<>();

//    static List<Bitmap>

    public static int STAND_BY = 0;
    public static int LOADING = 1;
    public static int SUCCESS = 2;
    public static int FAILURE = -1;

    public static String CHAR_URLS = "char_urls";

    Bitmap charImage = null;


    static MutableLiveData<Integer> status = new MutableLiveData<>();

    public static MutableLiveData<List<Character>> getCharacters() {
        return characters;
    }

    public static void loadCharacters(List<String> charUrls) {
        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(CharacterWorker.class)
                .setConstraints(new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build())
                .setInputData(new Data.Builder().putStringArray(CHAR_URLS, charUrls.toArray(new String[0])).build())
                .build();

        WorkManager.getInstance().beginUniqueWork(TAG, ExistingWorkPolicy.REPLACE, request).enqueue();
    }

    public static MutableLiveData<Integer> getStatus() {
        return status;
    }
}