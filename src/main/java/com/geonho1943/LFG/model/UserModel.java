package com.geonho1943.LFG.model;

import com.geonho1943.LFG.extraDB.User;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class UserModel implements UserRepository {

    private final DataSource dataSource;

    public UserModel(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    @Override
    public User join(User user) {
        String sql = "INSERT INTO LFGservice.lfg_user (user_id,user_pw,user_name) VALUES (?,?,?);";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, user.getUser_id());
            pstmt.setString(2, user.getUser_pw());
            pstmt.setString(3, user.getUser_name());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                //user.setUser_idx(Integer.parseInt(rs.getString(1)));
                user.setUser_idx(rs.getInt(1));
            } else {
                throw new SQLException("idx 조회 실패");
            }
            return user;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public List<User> findAll() {
        String sql = "select * from lfg_user;";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            List<User> users = new ArrayList<>();
            while (rs.next()) {
                User user = new User();
                user.setUser_idx(rs.getInt("user_idx"));
                user.setUser_id(rs.getString("user_id"));
                user.setUser_name(rs.getString("user_name"));
                user.setUser_pw(rs.getString("user_pw"));
                users.add(user);
            }
            return users;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public User pick(User user) {
        String sql = "select * from lfg_user where user_id = ?;";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, user.getUser_id());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                user.setUser_idx(rs.getInt("user_idx"));
                user.setUser_id(rs.getString("user_id"));
                user.setUser_pw(rs.getString("user_pw"));
                user.setUser_name(rs.getString("user_name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(conn, pstmt, rs);
        }
        return user;
    }

    @Override
    public User modify(User user) {
        String sql = "update lfg_user set user_id=?,user_pw=?,user_name=? where user_idx=?;";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, user.getUser_id());
            pstmt.setString(2, user.getUser_pw());
            pstmt.setString(3, user.getUser_name());
            pstmt.setInt(4, user.getUser_idx());
            pstmt.executeUpdate();
            return user;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public User sleep(User user) {
        String sql = "DELETE FROM lfg_user WHERE user_idx=?;";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, user.getUser_idx());
            pstmt.executeUpdate();
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
