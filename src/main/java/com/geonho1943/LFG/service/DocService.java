package com.geonho1943.LFG.service;

import com.geonho1943.LFG.dto.Doc;
import com.geonho1943.LFG.model.DocRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class DocService {
    
    private final DocRepository docRepository;
    @Autowired
    public DocService(DocRepository docRepository) {
        this.docRepository = docRepository;
    }

    public Doc read(Doc doc){
        return docRepository.read(doc);
    }

    public Doc post(Doc doc) {
        return docRepository.post(doc);
    }

    public List<Doc> list(int page) {
        int docStart = (page - 1) * 5;
        return docRepository.list(docStart);
    }

    public Doc modify(Doc doc) {
        return docRepository.modify(doc);
    }

    public void delete(Doc doc) {
        docRepository.delete(doc);
    }

    public List<Doc> appNameList(Doc doc, int page) {
        int docStart = (page - 1) * 5;
        return docRepository.appNameList(doc, docStart);
    }


    public int docCount() {
        return docRepository.docCount();
    }

    public int docSearchCount(String name) {
        return docRepository.docSearchCount(name);
    }
}
