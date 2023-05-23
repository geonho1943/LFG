package com.geonho1943.LFG.model;

import com.geonho1943.LFG.dto.User;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.*;

public class UserModel implements UserRepository {

    private final DataSource dataSource;

    public UserModel(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    @Override
    public User join(User user) {
        String sql = "INSERT INTO LFGservice.lfg_user (user_id, user_pw, user_name, user_reg) VALUES (?, SHA2(?, 256), ?, CURRENT_TIMESTAMP());";
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
    public User role(User user){
        String sql = "insert into LFGservice.lfg_user_role(user_idx,user_role) values (?,2);";
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

    @Override
    public User login(User user) {
        String sql = "SELECT*FROM LFGservice.lfg_user WHERE user_id=? AND user_pw=SHA2(?, 256)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, user.getUser_id());
            pstmt.setString(2, user.getUser_pw());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                user.setUser_idx(rs.getInt("user_idx"));
                user.setUser_id(rs.getString("user_id"));
                user.setUser_name(rs.getString("user_name"));
                user.setUser_reg(rs.getString("user_reg"));
                return user;
            }else {
                throw new SQLException("회원정보를 다시 입력하세요");
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public User auth(User user){
        String sql = "select user_role from LFGservice.lfg_user_role where user_idx = ?;";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,user.getUser_idx());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                user.setUser_role(rs.getInt("user_role"));
                return user;
            }else {
                throw new SQLException("! 관리자에게 문의 하세요 사유 : 권한");
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public boolean check(User user){
        String sql = "select user_id from LFGservice.lfg_user where user_id=?;";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, user.getUser_id());//id 검색후 중복이 없으면 user return
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return false;
            }else {
                return true;
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }
    @Override
    public User modify(User user) {
        String sql = "update LFGservice.lfg_user set user_id=?,user_pw=SHA2(?, 256),user_name=? where user_idx=?;";
        Connection conn = null;
        PreparedStatement pstmt = null;
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
            close(conn, pstmt, null);
        }
    }

    @Override
    public void sleep(User user) {
        String sql = "DELETE FROM LFGservice.lfg_user WHERE user_idx=?;";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, user.getUser_idx());
            pstmt.executeUpdate();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, null);
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
