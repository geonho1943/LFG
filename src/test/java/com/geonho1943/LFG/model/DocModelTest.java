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
import static org.junit.jupiter.api.Assertions.assertNull;

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
        Doc testForm = docInputForTest(1,"제목","작성자","내용","애플리케이션",1);

        // When
        Doc postDoc = docRepository.post(testForm);

        // Then
        Assertions.assertNotNull(postDoc);
        Assertions.assertEquals("제목", postDoc.getDoc_sub());
        Assertions.assertEquals("작성자", postDoc.getDoc_writ());
        Assertions.assertEquals("내용", postDoc.getDoc_cont());
        Assertions.assertEquals(1, postDoc.getDoc_app_id());
        Assertions.assertEquals("애플리케이션", postDoc.getDoc_app_name());
    }

    @Test
    void readTest() {
        // Given
        Doc testForm = docInputForTest(1,"제목","작성자","내용","애플리케이션",1);
        docRepository.post(testForm);
        // When
        Doc readDoc = docRepository.read(testForm);

        // Then
        Assertions.assertEquals(testForm.getDoc_sub(), readDoc.getDoc_sub());
        Assertions.assertEquals(testForm.getDoc_writ(), readDoc.getDoc_writ());
        Assertions.assertEquals(testForm.getDoc_cont(), readDoc.getDoc_cont());
        Assertions.assertEquals(testForm.getDoc_app_id(), readDoc.getDoc_app_id());
        Assertions.assertEquals(testForm.getDoc_app_name(), readDoc.getDoc_app_name());
    }

    @Test
    void listTest() {
        // When
        Doc testForm1 = docInputForTest(1,"제목1","작성자1","내용1","애플리케이션1",1);
        docRepository.post(testForm1);
        Doc testForm2 = docInputForTest(2,"제목2","작성자2","내용2","애플리케이션2",2);
        docRepository.post(testForm2);
        List<Doc> docs = docRepository.list();

        // Then
        Doc firstDoc = docs.get(0);
        Doc secondDoc = docs.get(1);
        Assertions.assertEquals(testForm2.getDoc_sub(), firstDoc.getDoc_sub());
        Assertions.assertEquals(testForm1.getDoc_sub(), secondDoc.getDoc_sub());
    }

    @Test
    void modifyTest() {
        // Given
        Doc testForm = docInputForTest(1,"제목","작성자","내용","애플리케이션",1);
        docRepository.post(testForm);

        // When
        Doc modifyTestForm = docInputForTest(1,"수정된 제목","작성자","수정된 내용","수정된 애플리케이션",2);
        docRepository.modify(testForm);

        // Then
        Assertions.assertEquals("수정된 제목", modifyTestForm.getDoc_sub());
        Assertions.assertEquals("수정된 내용", modifyTestForm.getDoc_cont());
        Assertions.assertEquals(2, modifyTestForm.getDoc_app_id());
        Assertions.assertEquals("수정된 애플리케이션", modifyTestForm.getDoc_app_name());
    }

    @Test
    void deleteTest() {
        // Given
        Doc testForm = docInputForTest(1,"제목","작성자","내용","애플리케이션",1);
        docRepository.post(testForm);

        // When
        docRepository.delete(testForm);
        Doc deleteDoc = new Doc();
        deleteDoc.setDoc_idx(testForm.getDoc_idx());
        deleteDoc = docRepository.read(deleteDoc);

        // Then
        assertNull(deleteDoc.getDoc_sub());
    }


    @Test
    void appNameListTest() {
        // Given
        Doc testForm1 = docInputForTest(1,"제목1","작성자1","내용1","애플리케이션1",1);
        docRepository.post(testForm1);
        Doc testForm2 = docInputForTest(2,"제목2","작성자2","내용2","애플리케이션1",1);
        docRepository.post(testForm2);
        Doc testForm3 = docInputForTest(3,"제목3","작성자3","내용3","애플리케이션1",1);
        docRepository.post(testForm3);

        // When
        List<Doc> docs = docRepository.appNameList(testForm1);

        // Then
        Assertions.assertNotNull(docs);
        Assertions.assertFalse(docs.isEmpty());
        for (Doc d : docs) {
            Assertions.assertEquals(1, d.getDoc_app_id());
            Assertions.assertEquals("애플리케이션1", d.getDoc_app_name());
        }
    }

    public Doc docInputForTest(int doc_idx, String doc_sub, String doc_writ, String doc_cont, String doc_app_name, int doc_app_id){
        Doc doc = new Doc();
        doc.setDoc_idx(doc_idx);
        doc.setDoc_sub(doc_sub);
        doc.setDoc_writ(doc_writ);
        doc.setDoc_cont(doc_cont);
        doc.setDoc_app_name(doc_app_name);
        doc.setDoc_app_id(doc_app_id);
        return doc;
    }
}