package com.davialbuquerque.myapplication.model;

import org.json.JSONObject;

public class Info {
    private long count;
    private long pages;
    private String next;
    private String prev;

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public long getPages() {
        return pages;
    }

    public void setPages(long pages) {
        this.pages = pages;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrev() {
        return prev;
    }

    public void setPrev(String prev) {
        this.prev = prev;
    }

    public static Info parseInfo(JSONObject json) {
        Info info = null;
        try {
            if (json.has("info")) {
                info = new Info();
                JSONObject infoJson = json.getJSONObject("info");
                info.count = infoJson.getLong("count");
                info.pages = infoJson.getLong("pages");
                info.next = infoJson.getString("next");
                info.prev = infoJson.getString("prev");
            }
        } catch (Exception e) {
            e.printStackTrace();
            info = null;
        }
        return info;
    }
}
