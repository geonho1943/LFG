package com.geonho1943.LFG.service;

import com.geonho1943.LFG.extraDB.Doc;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class DocService {

    public Doc read(Doc doc){
        return doc;
    }

}
