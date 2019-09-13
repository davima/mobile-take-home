package com.davialbuquerque.myapplication.view;

import com.davialbuquerque.myapplication.model.Character;
import com.davialbuquerque.myapplication.model.Episode;

public interface ItemClickListener {
    public void onEpisodeClick(Episode episode);

    public void onCharacterClick(Character character);
}
