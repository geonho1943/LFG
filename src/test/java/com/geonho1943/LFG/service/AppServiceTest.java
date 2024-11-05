package com.geonho1943.LFG.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.geonho1943.LFG.dto.App;
import com.geonho1943.LFG.model.AppRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@TestPropertySource(locations = "/application.yml") // 새로운 connection 설정을 사용함
@SpringBootTest
class AppServiceTest {

    @Autowired
    private DataSource dataSource;
    @Autowired
    AppRepository appRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private AppService appService;


    @BeforeEach
    void setUp() {
        String createSchema = "CREATE SCHEMA IF NOT EXISTS LFG_COLONY;";
        String createTable = "CREATE TABLE IF NOT EXISTS LFG_COLONY.lfg_app_list (" +
                "app_id INT NOT NULL," +
                "app_name VARCHAR(255) DEFAULT NULL," + // 45 > 200으로 증가함
                "PRIMARY KEY (`app_id`)" +
                ");"; // 백업해뒀던 lfg_app_list sql을 참고함
        jdbcTemplate.execute("DROP TABLE IF EXISTS `lfg_app_list`");
        jdbcTemplate.execute(createSchema);
        jdbcTemplate.execute(createTable);
    }

    @AfterEach
    void tearDown() {
        jdbcTemplate.execute("DROP TABLE IF EXISTS `lfg_app_list`");
    }

    @Test
    void apiParsingTest() {
        long startTime = System.currentTimeMillis();
        appService.apiParsing();
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
    }

    @Test
    void fieldClear() {
        // 데이터 생성
        ArrayList<App> apps = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            App app = new App();
            app.setApp_id(i+1);
            app.setApp_name("App test data " + i+1);
            apps.add(app);
        }
        appRepository.save(apps);
        // 데이터 삭제
        appRepository.deleteField();
        // 테이블 카운트
        String countRecord = "SELECT COUNT(*) FROM LFG_COLONY.lfg_app_list";
        Integer count = jdbcTemplate.queryForObject(countRecord, Integer.class);

        // 필드에 데이터가 남아있으면 실패
        Assertions.assertEquals(0, count);
        Assertions.assertNull(count); // 인티져라 null이 올수도 있음
    }

}