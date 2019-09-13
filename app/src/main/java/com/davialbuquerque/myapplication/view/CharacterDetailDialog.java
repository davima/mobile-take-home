package com.davialbuquerque.myapplication.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.davialbuquerque.myapplication.R;
import com.davialbuquerque.myapplication.api.CharacterRepo;
import com.davialbuquerque.myapplication.api.CharacterWorker;
import com.davialbuquerque.myapplication.model.Character;


public class CharacterDetailDialog extends AppCompatDialogFragment {

    public static final String CHARACTER = "CHARACTER";

    private LiveData<Bitmap> image;
    private Character character;
    private AppCompatImageView imageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            character = (Character) getArguments().getSerializable(CHARACTER);
        }

        View view = inflater.inflate(R.layout.character_dialog, container, false);
        imageView = view.findViewById(R.id.char_image);
        imageView.setImageDrawable(getContext().getDrawable(R.mipmap.ic_launcher));
        ((TextView) view.findViewById(R.id.char_id)).setText(getString(R.string.id, character.getId()));
        ((TextView) view.findViewById(R.id.char_name)).setText(getString(R.string.name, character.getName()));
        ((TextView) view.findViewById(R.id.char_status)).setText(getString(R.string.status, character.getStatus()));
        ((TextView) view.findViewById(R.id.char_species)).setText(getString(R.string.species, character.getSpecies()));
        ((TextView) view.findViewById(R.id.char_gender)).setText(getString(R.string.gender, character.getGender()));

        view.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        view.findViewById(R.id.kill_char).setEnabled(character.getStatus().equalsIgnoreCase("Alive"));
        view.findViewById(R.id.kill_char).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CharacterRepo.killCharacter(character.getId()))
                    getContext().getSharedPreferences(CharacterWorker.DEAD_IDS, Context.MODE_PRIVATE).edit().putBoolean("" + character.getId(), true).apply();
                dismiss();

            }
        });

        image = CharacterRepo.getLiveImage();
        CharacterRepo.loadImage(character.getImage());
        image.observe(this, new Observer<Bitmap>() {
            @Override
            public void onChanged(Bitmap bitmap) {
                if (bitmap != null) {
                    imageView.setImageBitmap(bitmap);
                } else {
                    imageView.setImageDrawable(getContext().getDrawable(R.mipmap.ic_launcher));
                }
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
