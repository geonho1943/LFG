package com.geonho1943.LFG.dto;

public class LoginInfo {

    private int user_idx;
    private String user_id;
    private String user_name;
    private int user_role;

    public int getUser_idx() {
        return user_idx;
    }

    public void setUser_idx(int user_idx) {
        this.user_idx = user_idx;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public int getUser_role() {
        return user_role;
    }

    public void setUser_role(int user_role) {
        this.user_role = user_role;
    }

    public LoginInfo(int user_idx, String user_id, String user_name, int user_role) {
        this.user_idx = user_idx;
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_role = user_role;
    }
}
