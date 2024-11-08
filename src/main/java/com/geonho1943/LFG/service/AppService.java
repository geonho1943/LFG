package com.geonho1943.LFG.service;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.geonho1943.LFG.dto.App;
import com.geonho1943.LFG.dto.Doc;
import com.geonho1943.LFG.model.AppRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Transactional
@Service
public class AppService {

    private final Logger logger = LoggerFactory.getLogger(AppService.class);
    private final AppRepository appRepository;

    @Autowired
    public AppService(AppRepository appRepository) {
        this.appRepository = appRepository;
    }

    public void fieldClear() {
        appRepository.deleteField();
    }

    public ResponseEntity<String> requestAppList() {
        String steamUrl = "https://api.steampowered.com/ISteamApps/GetAppList/v2/";
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForEntity(steamUrl, String.class);
    }

    public Set<App> apiParsing(ResponseEntity<String> response) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root = null;
        try {
            root = objectMapper.readTree(response.getBody());
        } catch (IOException e) {
            e.printStackTrace();
        }
        JsonNode appList = root.path("applist").path("apps");
        Set<App> apps = new HashSet<>();
        for (JsonNode app : appList) {
            App app1 = new App();
            app1.setApp_id(app.path("appid").asInt());
            app1.setApp_name(app.path("name").asText());
            apps.add(app1);
        }
        return apps;
    }

    public List<String> searchAppName(String name) {
        return appRepository.searchAppName(name);
    }

    public Doc searchAppId(Doc doc) {
        return appRepository.searchAppId(doc);
    }

    public void saveAppList(Set<App> apps) {
        appRepository.save(apps);
    }

    public void saveAppListWithMultiValue(Set<App> apps) {
        appRepository.saveAppsWithMultiValue(apps);
    }
}
