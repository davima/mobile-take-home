package com.davialbuquerque.myapplication.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.davialbuquerque.myapplication.R;
import com.davialbuquerque.myapplication.model.Character;

public class CharacterDetailDialog extends AppCompatDialogFragment {

    public static final String CHARACTER = "CHARACTER";

    Character character;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.character_dialog, container, false);
        if (getArguments() != null) {
            character = (Character) getArguments().getSerializable(CHARACTER);
        }



        return view;
    }
}
