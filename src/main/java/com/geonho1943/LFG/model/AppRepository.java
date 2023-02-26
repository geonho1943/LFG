package com.geonho1943.LFG.model;

import com.geonho1943.LFG.dto.App;

import java.util.List;

public interface AppRepository {
    void save(List<App> apps);

}
