package com.geonho1943.LFG.dto;

public class Doc {
    private int doc_idx;
    private String doc_sub;
    private String doc_writ;
    private String doc_cont;
    private String doc_reg;
    private String doc_app_name;
    private int Doc_app_id;

    public int getDoc_app_id() {
        return Doc_app_id;
    }

    public void setDoc_app_id(int doc_app_id) {
        Doc_app_id = doc_app_id;
    }

    public String getDoc_app_name() {
        return doc_app_name;
    }

    public void setDoc_app_name(String doc_app_name) {
        this.doc_app_name = doc_app_name;
    }

    public int getDoc_idx() {
        return doc_idx;
    }

    public void setDoc_idx(int doc_idx) {
        this.doc_idx = doc_idx;
    }

    public String getDoc_writ() {
        return doc_writ;
    }

    public void setDoc_writ(String doc_writ) {
        this.doc_writ = doc_writ;
    }

    public String getDoc_cont() {
        return doc_cont;
    }

    public void setDoc_cont(String doc_cont) {
        this.doc_cont = doc_cont;
    }

    public String getDoc_reg() {
        return doc_reg;
    }

    public void setDoc_reg(String doc_reg) {
        this.doc_reg = doc_reg;
    }

    public String getDoc_sub() {
        return doc_sub;
    }

    public void setDoc_sub(String doc_sub) {
        this.doc_sub = doc_sub;
    }
}
