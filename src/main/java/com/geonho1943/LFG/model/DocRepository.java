package com.geonho1943.LFG.model;

import com.geonho1943.LFG.dto.Doc;

import java.util.List;

public interface DocRepository {
    //crud
    Doc post (Doc doc);//작성
    Doc read(Doc doc);//조회
    Doc modify(Doc doc);//수정
    void delete(Doc doc);//삭제

    List<Doc> list(int docStart);
    List<Doc> appNameList(Doc doc, int docStart);

    int docCount();
    int docSearchCount(String name);
}
