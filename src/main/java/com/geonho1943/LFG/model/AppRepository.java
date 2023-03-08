package com.geonho1943.LFG.model;

import com.geonho1943.LFG.dto.App;
import com.geonho1943.LFG.dto.Doc;

import java.util.List;

public interface AppRepository {
    void rowClear();
    void save(List<App> apps);
    List<String> searchAppName(String name);

    Doc searchAppId(Doc doc);
}
