package com.davialbuquerque.myapplication.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.davialbuquerque.myapplication.R;
import com.davialbuquerque.myapplication.model.Character;

import java.util.ArrayList;
import java.util.List;

public class CharacterAdapter extends ListAdapter<Character, RecyclerView.ViewHolder> {

    private List<Character> characters = new ArrayList<>();
    private ItemClickListener listener;

    private static DiffUtil.ItemCallback<Character> DIFF = new DiffUtil.ItemCallback<Character>() {
        @Override
        public boolean areItemsTheSame(@NonNull Character oldItem, @NonNull Character newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areContentsTheSame(@NonNull Character oldItem, @NonNull Character newItem) {
            return oldItem.compareTo(newItem);
        }
    };

    CharacterAdapter() {
        super(DIFF);
    }

    void setEpisodeClickListener(ItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    protected Character getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getItemCount() {
        return characters.size();
    }

    void setData(List<Character> newEpisodeList) {
        this.characters.clear();
        this.characters.addAll(newEpisodeList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EpisodeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.character_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((EpisodeViewHolder) holder).bindTo(position);
    }

    private class EpisodeViewHolder extends RecyclerView.ViewHolder {
        Character character;

        EpisodeViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        void bindTo(int position) {
            character = characters.get(position);
            try {
                ((TextView) itemView.findViewById(R.id.character_name)).setText(character.getName());
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (listener != null)
                            listener.onCharacterClick(character);
                    }
                });
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
