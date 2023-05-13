package com.geonho1943.LFG.model;

import com.geonho1943.LFG.dto.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserModelTest {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserRepository userRepository;

    @Test
    void join() throws SQLException {
        //given
        User user = new User();
        user.setUser_id("test_id");
        user.setUser_pw("test_pw");
        user.setUser_name("test_name");

        //when
        User savedUser = userRepository.join(user);

        //then
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM lfg_user WHERE user_id = ?")) {
            pstmt.setString(1, "test_id");
            try (ResultSet rs = pstmt.executeQuery()) {
                assertTrue(rs.next());
                assertEquals(rs.getString("user_id"), savedUser.getUser_id());
                assertEquals(rs.getString("user_name"), savedUser.getUser_name());
            }
        }
    }

    @Test
    void findAll() {
        //given
        //when
        //then
    }

    @Test
    void pick() {
        //given
        //when
        //then
    }

    @Test
    void check() {
        //given
        //when
        //then
    }

    @Test
    void modify() {
        //given
        //when
        //then
    }

    @Test
    void sleep() {
        //given
        //when
        //then
    }
}