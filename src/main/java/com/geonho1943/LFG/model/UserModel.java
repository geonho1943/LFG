package com.geonho1943.LFG.model;

import com.geonho1943.LFG.extraDB.ExtraDB;

//DB에 user에 대한 저장,조회를 위한 기능들
public class UserModel extends ExtraDB {
    private int idx=0;

    public String test(String test){
        setName(test);
        return getName();
    }

    public String join(String name,String pw){
        //setIdx(++idx);
        setName(name);
        setPw(pw);
        return getName()+" "+getPw()+"success!";
        //!!!!
    }
    public String lookup(){
        return String.valueOf(getIdx())+" "+getName();
    }

}
