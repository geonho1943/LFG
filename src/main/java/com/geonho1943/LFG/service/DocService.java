package com.geonho1943.LFG.service;

import com.geonho1943.LFG.extraDB.Doc;
import com.geonho1943.LFG.extraDB.User;
import com.geonho1943.LFG.model.DocRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class DocService {
    
    private final DocRepository docRepository;
    public DocService(DocRepository docRepository) {
        this.docRepository = docRepository;
    }

    public Doc read(Doc doc){
        docRepository.read(doc);
        return doc;
    }

    public Doc post(Doc doc) {
        docRepository.post(doc);
        return doc;
    }

    public List<Doc> list() {
        return docRepository.list();
    }
}
