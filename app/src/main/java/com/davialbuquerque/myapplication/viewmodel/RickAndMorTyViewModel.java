package com.davialbuquerque.myapplication.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class RickAndMorTyViewModel<T> extends ViewModel {

    private MutableLiveData<List<T>> data;
    private MutableLiveData<Integer> status;

    public void init(MutableLiveData<List<T>> liveData, MutableLiveData<Integer> status) {
        this.status = status;
        this.data = liveData;
    }

    public LiveData<List<T>> getData() {
        return data;
    }

    public LiveData<Integer> getStatus() {
        return status;
    }
}
