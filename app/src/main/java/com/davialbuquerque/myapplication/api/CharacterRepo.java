package com.davialbuquerque.myapplication.api;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
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
    private static final String TAG_IMAGE = "getCharacterImage";
    static MutableLiveData<List<Character>> characters = new MutableLiveData<>();
    static List<Character> backedCharacters = new ArrayList<>();

    static MutableLiveData<Bitmap> characterImage = new MutableLiveData<>();

    public static int STAND_BY = 0;
    public static int LOADING = 1;
    public static int SUCCESS = 2;
    public static int FAILURE = -1;

    public static String CHAR_URLS = "char_urls";
    public static String IMAGE_URL = "image_urls";

    static MutableLiveData<Integer> status = new MutableLiveData<>();

    public static MutableLiveData<List<Character>> getCharacters() {
        return characters;
    }

    public static MutableLiveData<Integer> getStatus() {
        return status;
    }

    static MutableLiveData<Bitmap> getImage() {
        return characterImage;
    }

    public static LiveData<Bitmap> getLiveImage() {
        return characterImage;
    }

    public static void loadCharacters(List<String> charUrls) {
        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(CharacterWorker.class)
                .setConstraints(new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build())
                .setInputData(new Data.Builder().putStringArray(CHAR_URLS, charUrls.toArray(new String[0])).build())
                .build();

        WorkManager.getInstance().beginUniqueWork(TAG, ExistingWorkPolicy.REPLACE, request).enqueue();
    }

    public static void loadImage(String url) {
        characterImage.postValue(null);
        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(CharacterWorker.class)
                .setConstraints(new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build())
                .setInputData(new Data.Builder().putString(IMAGE_URL, url).build())
                .build();

        WorkManager.getInstance().beginUniqueWork(TAG_IMAGE, ExistingWorkPolicy.REPLACE, request).enqueue();
    }

    public static boolean killCharacter(long id) {
        for (Character character : backedCharacters) {
            if (character.getId() == id) {
                character.setStatus("Dead");
                characters.postValue(backedCharacters);
                return true;
            }
        }
        return false;
    }

}