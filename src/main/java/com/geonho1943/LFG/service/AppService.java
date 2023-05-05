package com.geonho1943.LFG.service;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.geonho1943.LFG.controller.AppController;
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
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class AppService {
    private final Logger LOGGER = LoggerFactory.getLogger(AppService.class);
    private final AppRepository appRepository;
    @Autowired
    public AppService(AppRepository appRepository) {
        this.appRepository = appRepository;
    }

    public void rowClear() {
        appRepository.rowClear();
    }

    public void apiParsing() {
        long startTime = System.currentTimeMillis();
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
            App app1 = new App();
            app1.setApp_id(app.path("appid").asInt());
            app1.setApp_name(app.path("name").asText());
            apps.add(app1);
        }
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        appRepository.save(apps);
        LOGGER.info("API 파싱 작업이 완료되었습니다. 소요시간: " + elapsedTime + "ms");
    }

    public List<String> searchAppName(String name) {
        return appRepository.searchAppName(name);

    }

    public Doc searchAppId(Doc doc) {
        return appRepository.searchAppId(doc);
    }
}
