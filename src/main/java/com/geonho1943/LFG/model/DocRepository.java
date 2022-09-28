package com.geonho1943.LFG.model;

import com.geonho1943.LFG.extraDB.Doc;
import com.geonho1943.LFG.extraDB.User;

import java.util.List;

public interface DocRepository {
    //crud
    Doc post (Doc doc);//작성
    Doc read(Doc doc);//조회
    Doc modify(Doc doc);//수정
    Doc delete(Doc doc);//삭제

    List<Doc> list();
}
