package com.geonho1943.LFG.model;

import com.geonho1943.LFG.extraDB.User;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.*;

import static java.sql.DriverManager.getConnection;

public class UserModel implements UserRepository {

    private final DataSource dataSource;

    public UserModel(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    @Override
    public User join(User user) {
        String sql = "INSERT INTO `LFGservice`.`lfg_user` (`user_id`,`user_pw`,`user_name`) VALUES (?,?,?);";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, user.getUser_id());
            pstmt.setString(2,user.getUser_pw());
            pstmt.setString(3,user.getUser_name());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                user.setUser_id(rs.getString(1));
            } else {
                throw new SQLException("id 조회 실패");
            }
            return user;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }
    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs){
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                close(conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void close(Connection conn) throws SQLException {
        DataSourceUtils.releaseConnection(conn, dataSource);
    }
}
