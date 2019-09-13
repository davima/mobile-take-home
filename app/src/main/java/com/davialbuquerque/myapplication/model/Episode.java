package com.davialbuquerque.myapplication.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Episode implements Serializable {
    private Long id;
    private String name;
    private String airDate;
    private String episode;
    private List<String> characters;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAirDate() {
        return airDate;
    }

    public void setAirDate(String airDate) {
        this.airDate = airDate;
    }

    public String getEpisode() {
        return episode;
    }

    public void setEpisode(String episode) {
        this.episode = episode;
    }

    public List<String> getCharacters() {
        return characters;
    }

    public void setCharacters(List<String> characters) {
        this.characters = characters;
    }

    public static Episode parseEpisode(JSONObject json) {
        Episode result = null;

        try {
            result = new Episode();
            result.id = json.getLong("id");
            result.name = json.getString("name");
            result.airDate = json.getString("id");
            result.episode = json.getString("id");
            result.characters = new ArrayList<>();
            JSONArray charArray = json.getJSONArray("characters");
            for (int i = 0; i < charArray.length(); i++ ) {
                result.characters.add(charArray.getString(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = null;
        }

        return result;
    }

    public boolean compareTo(Episode newItem) {
        return this.episode.equals(newItem.episode);
    }
}
