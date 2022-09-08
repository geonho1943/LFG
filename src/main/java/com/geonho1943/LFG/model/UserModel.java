package com.geonho1943.LFG.model;

import com.geonho1943.LFG.extraDB.ExtraDB;
//DB에 user에 대한 저장,조회를 위한 기능들
public class UserModel extends ExtraDB {
    private int idx=0;

    public String save(String name){
        setIdx(++idx);
        setName(name);
        return "ok";
    }
    public String lookup(){
        String asd = String.valueOf(getIdx())+getName();
        return asd;
    }

}
