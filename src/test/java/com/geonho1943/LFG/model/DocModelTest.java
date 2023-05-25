package com.geonho1943.LFG.model;

import com.geonho1943.LFG.dto.Doc;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

import javax.sql.DataSource;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestPropertySource(locations = "/application-test.properties")
@SpringBootTest
class DocModelTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private DocRepository docRepository;

    private final Logger LOGGER = LoggerFactory.getLogger(UserModelTest.class);

    @BeforeEach
    void setUp() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String createSchemaQuery = "CREATE SCHEMA IF NOT EXISTS LFGSERVICE;";
        String createTableSql = "CREATE TABLE IF NOT EXISTS `LFGservice`.`lfg_doc` (" +
                "`doc_idx` INT AUTO_INCREMENT NOT NULL," +
                "`doc_sub` VARCHAR(45) NOT NULL," +
                "`doc_writ` VARCHAR(45) NOT NULL," +
                "`doc_cont` VARCHAR(180) NOT NULL," +
                "`doc_reg` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP," +
                "`doc_app_id` INT DEFAULT NULL," +
                "`doc_app_name` VARCHAR(45) DEFAULT NULL," +
                "PRIMARY KEY (`doc_idx`)," +
                "UNIQUE KEY `doc_idx_UNIQUE` (`doc_idx`)" +
                ")";
        jdbcTemplate.execute(createSchemaQuery);
        jdbcTemplate.execute(createTableSql);
        LOGGER.info("H2 데이터베이스의 스키마,테이블 생성이 완료 되었습니다.");
    }

    @AfterEach
    void tearDown() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String dropDocTable = "DROP TABLE IF EXISTS `lfg_doc`";
        jdbcTemplate.execute(dropDocTable);
        LOGGER.info("doc Table 을 삭제 합니다.");
    }

    @Test
    void postTest() {
        // Given
        Doc doc = new Doc();
        doc.setDoc_sub("제목");
        doc.setDoc_writ("작성자");
        doc.setDoc_cont("내용");
        doc.setDoc_app_id(1);
        doc.setDoc_app_name("애플리케이션");

        // When
        Doc postDoc = docRepository.post(doc);

        // Then
        Assertions.assertNotNull(postDoc);
        Assertions.assertEquals("제목", doc.getDoc_sub());
        Assertions.assertEquals("작성자", doc.getDoc_writ());
        Assertions.assertEquals("내용", doc.getDoc_cont());
        Assertions.assertEquals(1, doc.getDoc_app_id());
        Assertions.assertEquals("애플리케이션", doc.getDoc_app_name());
    }

    @Test
    void readTest() {
        // Given
        Doc doc = new Doc();
        doc.setDoc_sub("제목");
        doc.setDoc_writ("작성자");
        doc.setDoc_cont("내용");
        doc.setDoc_app_id(1);
        doc.setDoc_app_name("애플리케이션");
        docRepository.post(doc);
        // When
        Doc readDoc = docRepository.read(doc);

        // Then
        Assertions.assertEquals(doc.getDoc_sub(), readDoc.getDoc_sub());
        Assertions.assertEquals(doc.getDoc_writ(), readDoc.getDoc_writ());
        Assertions.assertEquals(doc.getDoc_cont(), readDoc.getDoc_cont());
        Assertions.assertEquals(doc.getDoc_app_id(), readDoc.getDoc_app_id());
        Assertions.assertEquals(doc.getDoc_app_name(), readDoc.getDoc_app_name());
    }

    @Test
    void listTest() {
        // When
        Doc doc1 = new Doc();
        doc1.setDoc_sub("제목1");
        doc1.setDoc_writ("작성자1");
        doc1.setDoc_cont("내용1");
        doc1.setDoc_app_id(1);
        doc1.setDoc_app_name("애플리케이션1");
        docRepository.post(doc1);
        Doc doc2 = new Doc();
        doc2.setDoc_sub("제목2");
        doc2.setDoc_writ("작성자2");
        doc2.setDoc_cont("내용2");
        doc2.setDoc_app_id(2);
        doc2.setDoc_app_name("애플리케이션2");
        docRepository.post(doc2);
        List<Doc> docs = docRepository.list();

        // Then
        Doc firstDoc = docs.get(0);
        Doc secondDoc = docs.get(1);
        Assertions.assertEquals("제목2", firstDoc.getDoc_sub());
        Assertions.assertEquals("제목1", secondDoc.getDoc_sub());
    }

    @Test
    void modifyTest() {
        // Given
        Doc doc = new Doc();
        doc.setDoc_sub("제목");
        doc.setDoc_writ("작성자");
        doc.setDoc_cont("내용");
        doc.setDoc_app_id(1);
        doc.setDoc_app_name("애플리케이션");
        docRepository.post(doc);

        // When
        doc.setDoc_idx(1);
        doc.setDoc_sub("수정된 제목");
        doc.setDoc_cont("수정된 내용");
        doc.setDoc_app_id(2);
        doc.setDoc_app_name("수정된 애플리케이션");
        docRepository.modify(doc);

        // Then
        Assertions.assertEquals("수정된 제목", doc.getDoc_sub());
        Assertions.assertEquals("수정된 내용", doc.getDoc_cont());
        Assertions.assertEquals(2, doc.getDoc_app_id());
        Assertions.assertEquals("수정된 애플리케이션", doc.getDoc_app_name());
    }

    @Test
    void deleteTest() {
        // Given
        Doc doc = new Doc();
        doc.setDoc_sub("제목");
        doc.setDoc_writ("작성자");
        doc.setDoc_cont("내용");
        doc.setDoc_app_id(1);
        doc.setDoc_app_name("애플리케이션");
        docRepository.post(doc);

        // When
        docRepository.delete(doc);

        // Then
        Doc readDoc = new Doc();
        readDoc.setDoc_idx(doc.getDoc_idx());
        Doc deleteDoc = docRepository.read(readDoc);

        assertEquals( null, deleteDoc.getDoc_sub());
    }


    @Test
    void appNameListTest() {
        // Given
        Doc doc = new Doc();
        doc.setDoc_sub("제목");
        doc.setDoc_writ("작성자");
        doc.setDoc_cont("내용");
        doc.setDoc_app_id(0);
        doc.setDoc_app_name("애플리케이션");
        docRepository.post(doc);
        Doc doc1 = new Doc();
        doc1.setDoc_sub("제목");
        doc1.setDoc_writ("작성자1");
        doc1.setDoc_cont("내용1");
        doc1.setDoc_app_id(1);
        doc1.setDoc_app_name("애플리케이션");
        docRepository.post(doc1);
        Doc doc2 = new Doc();
        doc2.setDoc_sub("제목");
        doc2.setDoc_writ("작성자2");
        doc2.setDoc_cont("내용2");
        doc2.setDoc_app_id(2);
        doc2.setDoc_app_name("애플리케이션");
        docRepository.post(doc2);

        // When
        List<Doc> docs = docRepository.appNameList(doc);

        // Then
        Assertions.assertNotNull(docs);
        Assertions.assertFalse(docs.isEmpty());
        for (Doc d : docs) {
            Assertions.assertEquals("제목", d.getDoc_sub());
            Assertions.assertEquals("애플리케이션", d.getDoc_app_name());
        }
    }
}