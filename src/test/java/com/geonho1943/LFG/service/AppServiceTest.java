package com.geonho1943.LFG.service;

import com.geonho1943.LFG.dto.App;
import com.geonho1943.LFG.model.AppRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import javax.sql.DataSource;
import java.util.HashSet;
import java.util.Set;

@TestPropertySource(locations = "/application.yml") // 새로운 connection 설정을 사용함
@SpringBootTest
class AppServiceTest {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private AppRepository appRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private AppService appService;

    @BeforeEach
    void setUp() {
        try {
            jdbcTemplate.execute("CREATE TABLE LFG_COLONY.LFG_APP_LIST (" +
                    "app_id INT NOT NULL," +
                    "app_name VARCHAR(255) DEFAULT NULL" +  // 45 > 200으로 증가함
                    ",PRIMARY KEY (app_id)" +
                    ");"); // 백업해뒀던 lfg_app_list sql을 참고함
        } catch (Exception e) {
            System.err.println("테이블 생성 중 오류 발생: " + e.getMessage());
            throw new RuntimeException("테이블 생성 실패", e);
        }
    }

    @AfterEach
    void tearDown() {
        try {
            jdbcTemplate.execute("DROP TABLE LFG_COLONY.LFG_APP_LIST;");
        } catch (Exception e) {
            System.err.println("테이블 정리 중 오류 발생: " + e.getMessage());
        }
    }

    @Test
    void parsingAndSaveTest() {
        ResponseEntity<String> respondedApps = appService.requestAppList();
        if (respondedApps.getStatusCode() == HttpStatus.OK) {
            long startTime, endTime;
            Set<App> appList = appService.apiParsing(respondedApps);

            startTime = System.currentTimeMillis();
            appService.saveAppList(appList); // 기존 메서드
            endTime = System.currentTimeMillis();
            long saveAppListTime = endTime - startTime;
            System.out.println("saveAppList: " + saveAppListTime + " ms");

            tearDown();
            setUp(); //  테스트 메서드 실행시 동작되는 테이블 초기화

            startTime = System.currentTimeMillis();
            appService.saveAppListWithMultiValue(appList); // 개선된 메서드
            endTime = System.currentTimeMillis();
            System.out.println("상향된 multi value + batch 메서드");
            long multiValTime = endTime - startTime;
            System.out.println("multiValTime: " + multiValTime + " ms");
        }
    }

    @Test
    void fieldClear() {
        // 데이터 생성
        Set<App> apps = new HashSet<>();
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
    }

}