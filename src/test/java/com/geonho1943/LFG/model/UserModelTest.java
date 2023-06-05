package com.geonho1943.LFG.model;

import com.geonho1943.LFG.dto.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

@TestPropertySource(locations = "/application-test.properties")
@SpringBootTest
class UserModelTest {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserRepository userRepository;

    private final Logger logger = LoggerFactory.getLogger(UserModelTest.class);

    @BeforeEach
    public void setUp() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String createSchemaSql = "CREATE SCHEMA IF NOT EXISTS LFGSERVICE;";
        String createTableSql = "CREATE TABLE IF NOT EXISTS LFGservice.lfg_user"
        + "(user_idx INT AUTO_INCREMENT NOT NULL, user_id VARCHAR(45) NOT NULL,"
        + "user_pw VARCHAR(128) NOT NULL, user_name VARCHAR(45) NOT NULL, user_reg TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,"
        + "PRIMARY KEY (user_idx), UNIQUE (user_idx))";
        String createSha2Sql ="CREATE ALIAS SHA2 FOR 'com.geonho1943.LFG.utils.H2CustomFunctions.sha2'";
        jdbcTemplate.execute(createSchemaSql);
        jdbcTemplate.batchUpdate(createTableSql);
        jdbcTemplate.batchUpdate(createSha2Sql);
        logger.info("H2 데이터베이스의 스키마,테이블 생성이 완료 되었습니다.");
    }
    
    @AfterEach
    public void afterEach(){
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String dropSha2Sql = "DROP ALIAS IF EXISTS SHA2";
        String dropTableSql = "DROP TABLE IF EXISTS LFGservice.lfg_user";
        String dropRoleTableSql = "DROP TABLE IF EXISTS LFGservice.lfg_user_role";
        jdbcTemplate.execute(dropSha2Sql);
        jdbcTemplate.batchUpdate(dropTableSql);
        jdbcTemplate.batchUpdate(dropRoleTableSql);
        logger.info("커스텀 함수를 삭제하였습니다.");
    }
    public void roleSetUp(){
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String createTableSql = "CREATE TABLE IF NOT EXISTS LFGservice.lfg_user_role"
        + " (`user_idx` int(11) NOT NULL,`user_role` int(11) NOT NULL,"
        + "PRIMARY KEY (`user_idx`), UNIQUE KEY `user_idx_UNIQUE` (`user_idx`))";
        jdbcTemplate.execute(createTableSql);
        logger.info("H2: lfg_user_role Table이  생성이 완료 되었습니다.");
    }

    @Test
    void joinTest() throws SQLException {
        //given

        User user = new User();
        user.setUser_id("test_id");
        user.setUser_pw("test_pw");
        user.setUser_name("test_name");

        //when
        User savedUser = userRepository.join(user);

        //then
        try (Connection conn = dataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM LFGservice.lfg_user WHERE user_id = ?")) {
            pstmt.setString(1, "test_id");
            try (ResultSet rs = pstmt.executeQuery()) {
                assertTrue(rs.next());
                assertEquals(savedUser.getUser_id(), rs.getString("user_id"));
                assertEquals(savedUser.getUser_name(), rs.getString("user_name"));
            }
        }
        logger.info("join 검증이 완료 되었습니다.");
    }

    @Test
    void loginTest() throws SQLException {
        // given
        User user = new User();
        user.setUser_id("test_id");
        user.setUser_pw("test_pw");
        user.setUser_name("test_name");
        userRepository.join(user);

        // when
        User loginUser = new User();
        loginUser.setUser_id("test_id");
        loginUser.setUser_pw("test_pw");
        User loggedInUser = userRepository.login(loginUser);

        // then
        assertNotNull(loggedInUser);
        assertEquals("test_id", loggedInUser.getUser_id());
        assertEquals("test_name", loggedInUser.getUser_name());

        logger.info("login 검증이 완료 되었습니다.");
    }

    @Test
    void loginFailTest() {
        //Given
        User user = new User();
        user.setUser_id("test_id");
        user.setUser_pw("test_pw");

        // When
        try{
            userRepository.login(user);
            fail("정상 로그인 됨: 로그인 실패가 검증되지 않았습니다");
        } catch (IllegalArgumentException e) {
            assertEquals(IllegalArgumentException.class, e.getClass());
        }
        logger.info("login 예외 검증이 완료 되었습니다.");
    }

    @Test
    void roleTest() throws SQLException {
        // Given
        roleSetUp();
        User user = new User();
        user.setUser_id("test_id");
        user.setUser_pw("test_pw");
        user.setUser_name("test_name");
        userRepository.join(user);

        // When
        userRepository.role(user);
        userRepository.auth(user);

        // Then
        assertEquals(2, user.getUser_role());
        logger.info("권한 할당 검증이 완료되었습니다.");
    }

    @Test
    void authTest() {
        // given
        roleSetUp();
        User user = new User();
        user.setUser_id("test_id2");//user_role 2 사용자 계정
        user.setUser_pw("test_pw2");
        user.setUser_name("test_name2");
        userRepository.join(user);
        userRepository.role(user);

        // when
        userRepository.auth(user);
        // then
        assertEquals(2,user.getUser_role());
        logger.info("권힌 조회 검증이 완료 되었습니다.");
    }

    @Test
    void checkTest() {
        // given
        User user = new User();
        user.setUser_id("test_id_true");
        User user1 = new User();
        user1.setUser_id("test_id_false");
        user1.setUser_pw("test_pw");
        user1.setUser_name("test_name");
        userRepository.join(user1);

        // when
        boolean idcheck = userRepository.check(user);
        boolean idFalseCheck = userRepository.check(user1);

        // then
        assertEquals(true, idcheck);
        logger.info("사용가능 ID 조회 검증을 성공 했습니다");
        assertEquals(false, idFalseCheck);
        logger.info("사용불가 ID 조회 검증을 성공 했습니다");
    }

    @Test
    void modifyTest() {
        // given
        User user = new User();
        user.setUser_id("test_id");
        user.setUser_pw("test_pw");
        user.setUser_name("test_name");
        userRepository.join(user);

        // when
        user.setUser_id("new_test_id");
        user.setUser_pw("new_test_pw");
        user.setUser_name("new_test_name");
        userRepository.modify(user);
        // then
        assertEquals("new_test_id", user.getUser_id());
        assertEquals("new_test_pw", user.getUser_pw());
        assertEquals("new_test_name", user.getUser_name());
    }

    @Test
    void sleepTest() throws SQLException {
        // Given
        User user = new User();
        user.setUser_id("sleep_test_id");
        user.setUser_pw("sleep_test_pw");
        user.setUser_name("sleep_test_name");
        userRepository.join(user);

        // When
        userRepository.sleep(user);

        // Then
        try {
            userRepository.login(user);
            fail("회원정보가 정상조회됨: 회원 정보가 정상적으로 삭제 되지 않았습니다");
        } catch (IllegalArgumentException e) {
            assertEquals(IllegalArgumentException.class, e.getClass());
        }
        logger.info("로그인 예외 처리 검증이 완료되었습니다.");
    }

}