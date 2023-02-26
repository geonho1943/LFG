package com.geonho1943.LFG.model;

import com.geonho1943.LFG.dto.App;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class AppModel implements AppRepository {
    private final DataSource dataSource;

    public AppModel(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void save(List<App> apps) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = getConnection();
            String sql = "INSERT INTO `LFGservice`.`lfg_app_list` (app_id, app_name) VALUES (?, ?);";
            pstmt = conn.prepareStatement(sql);

            for (App app : apps) {
                pstmt.setInt(1, app.getApp_id());
                pstmt.setString(2, app.getName());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        } catch (SQLException e) {
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
