package com.davialbuquerque.myapplication.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.davialbuquerque.myapplication.api.CharacterRepo;
import com.davialbuquerque.myapplication.model.Character;

import java.util.List;

public class CharacterViewModel extends ViewModel {

    private MutableLiveData<List<Character>> characters;
    private MutableLiveData<Integer> status;

    public void init() {
        status = CharacterRepo.getStatus();
        characters = CharacterRepo.getCharacters();
    }

    public LiveData<List<Character>> getCharacters() {
        return characters;
    }

    public LiveData<Integer> getStatus() {
        return status;
    }

}
