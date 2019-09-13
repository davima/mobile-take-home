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
import com.davialbuquerque.myapplication.model.Episode;

import java.util.ArrayList;
import java.util.List;

public class EpisodeAdapter extends ListAdapter<Episode, RecyclerView.ViewHolder> {

    private List<Episode> episodes = new ArrayList<>();
    private ItemClickListener listener;

    private static DiffUtil.ItemCallback<Episode> DIFF = new DiffUtil.ItemCallback<Episode>() {
        @Override
        public boolean areItemsTheSame(@NonNull Episode oldItem, @NonNull Episode newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areContentsTheSame(@NonNull Episode oldItem, @NonNull Episode newItem) {
            return oldItem.compareTo(newItem);
        }
    };

    EpisodeAdapter() {
        super(DIFF);
    }

    void setEpisodeClickListener(ItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    protected Episode getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getItemCount() {
        return episodes.size();
    }

    void setData(List<Episode> newEpisodeList) {
        this.episodes.clear();
        this.episodes.addAll(newEpisodeList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EpisodeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.episode_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((EpisodeViewHolder) holder).bindTo(position);
    }

    private class EpisodeViewHolder extends RecyclerView.ViewHolder {
        Episode episode;

        EpisodeViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        void bindTo(int position) {
            episode = episodes.get(position);

            ((TextView) itemView.findViewById(R.id.episode_name)).setText(episode.getName());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null)
                        listener.onEpisodeClick(episode);
                }
            });
        }
    }
}
