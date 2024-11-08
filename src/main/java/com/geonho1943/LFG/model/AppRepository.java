package com.geonho1943.LFG.model;

import com.geonho1943.LFG.dto.App;
import com.geonho1943.LFG.dto.Doc;

import java.util.List;
import java.util.Set;

public interface AppRepository {
    void deleteField();
    void save(Set<App> apps);
    void saveAppsWithMultiValue(Set<App> apps);
    List<String> searchAppName(String name);
    Doc searchAppId(Doc doc);
}
