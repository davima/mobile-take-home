package com.davialbuquerque.myapplication.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EpisodeResponse {
    private Info info;
    private List<Episode> episodes;

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public List<Episode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<Episode> episodes) {
        this.episodes = episodes;
    }

    public static EpisodeResponse parseEpisodeResponse(JSONObject json) {
        EpisodeResponse response = null;
        try {
            Info info = Info.parseInfo(json);
            if (info != null && json.has("results")) {
                JSONArray episodeArray = json.getJSONArray("results");
                response = new EpisodeResponse();
                response.info = info;
                response.episodes = new ArrayList<>();
                for (int i = 0; i < episodeArray.length(); i++) {
                    response.episodes.add(Episode.parseEpisode(episodeArray.getJSONObject(i)));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response = null;
        }

        return response;
    }
}
