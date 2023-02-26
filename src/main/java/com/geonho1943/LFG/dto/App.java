package com.geonho1943.LFG.dto;

public class App {
    private int app_id;
    private String name;

    public App(int app_id, String name) {
        this.app_id = app_id;
        this.name = name;
    }

    public int getApp_id() {
        return app_id;
    }

    public void setApp_id(int app_id) {
        this.app_id = app_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
