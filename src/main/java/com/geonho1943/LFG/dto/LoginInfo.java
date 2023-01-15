package com.geonho1943.LFG.dto;

public class LoginInfo {

    private int user_idx;
    private String user_id;
    private String user_name;
    private int user_role;

    public LoginInfo(int user_idx, String user_id, String user_name, int user_role) {
        this.user_idx = user_idx;
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_role = user_role;
    }
}
