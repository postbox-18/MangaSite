package com.example.mangasite;

public class MangaSiteList {
    private final String name,site;
    public MangaSiteList(String name, String site) {
        this.name = name;
        this.site = site;
    }

    public String getName() {
        return name;
    }

    public String getSite() {
        return site;
    }
}
