package com.davialbuquerque.myapplication.model;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.prefs.Preferences;

public class Character implements Serializable {
    private long id;
    private String name;
    private String status;
    private String species;
    private String type;
    private String gender;
    private Date created;
    private List<String> episode;
    private String location;
    private String image;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public List<String> getEpisode() {
        return episode;
    }

    public void setEpisode(List<String> episode) {
        this.episode = episode;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public static Character fromJson(JSONObject json, SharedPreferences pref) {
        try {
            Character character = new Character();
            character.id = json.getLong("id");
            character.name = json.getString("name");
            if (pref.contains(""+character.id)) {
                character.status = "Dead";
            } else {
                character.status = json.getString("status");
            }
            character.species = json.getString("species");
            character.type = json.getString("type");
            character.gender = json.getString("gender");
            try {
                character.created = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).parse(json.getString("created"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            character.location = json.getString("location");
            character.image = json.getString("image");

            return character;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Boolean compareTo(Character character) {
        return this.id == character.id && this.status.equals(character.status);
    }
}
