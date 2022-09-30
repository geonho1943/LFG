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
        return docRepository.read(doc);
    }

    public Doc post(Doc doc) {
        return docRepository.post(doc);
    }

    public List<Doc> list() {
        return docRepository.list();
    }

    public Doc modify(Doc doc) {
        return docRepository.modify(doc);
    }

    public Doc delete(Doc doc) {
        return docRepository.delete(doc);
    }
}
