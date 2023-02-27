package com.geonho1943.LFG.service;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.geonho1943.LFG.dto.App;
import com.geonho1943.LFG.model.AppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class AppService {

    private final AppRepository appRepository;
    @Autowired
    public AppService(AppRepository appRepository) {
        this.appRepository = appRepository;
    }

    public void rowClear() {
        appRepository.rowClear();
    }

    public void apiParsing() {
        String steamUrl = "https://api.steampowered.com/ISteamApps/GetAppList/v2/";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(steamUrl, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root = null;
        try {
            root = objectMapper.readTree(response.getBody());
        } catch (IOException e) {
            e.printStackTrace();
        }
        JsonNode appList = root.path("applist").path("apps");
        List<App> apps = new ArrayList<>();
        for (JsonNode app : appList) {
            int app_id = app.path("appid").asInt();
            String name = app.path("name").asText();
            App app1 = new App(app_id, name);
            apps.add(app1);
        }
        appRepository.save(apps);
    }
}
