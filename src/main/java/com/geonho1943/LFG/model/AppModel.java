package com.geonho1943.LFG.model;

import com.geonho1943.LFG.dto.App;
import com.geonho1943.LFG.dto.Doc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceUtils;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AppModel implements AppRepository {

    private final Logger logger = LoggerFactory.getLogger(AppModel.class);
    private final DataSource dataSource;

    public AppModel(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void save(Set<App> apps) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            String sql = "INSERT INTO LFG_COLONY.LFG_APP_LIST (app_id, app_name) VALUES (?, ?)";
            int count=0;
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            HashSet<Integer> dupli = new HashSet<>();
            for (App app : apps) {
                count++;
                if (dupli.contains(app.getApp_id())) {
                    continue; // 이미 추가된 id는 스킵
                }
                dupli.add(app.getApp_id());
                pstmt.setInt(1, app.getApp_id());
                pstmt.setString(2, app.getApp_name());
                pstmt.addBatch();
                if (count >= 1000) {
                    pstmt.executeBatch();
                    count = 0;
                }
            }
            if (count > 0) pstmt.executeBatch(); // 나머지 정리
        } catch (Exception e) {
            logger.warn("lfg_app_list 테이블의 save 예외발생: " + e.getMessage() +
                    ".\n데이터 갱신에 실패했습니다. 관리자의 확인이 필요합니다.");
            throw new IllegalStateException(e);
        } finally {
            logger.info("lfg_app_list테이블의 갱신 - save가 진행 되었습니다");
            close(conn, pstmt, null);
        }
    }

    @Override
    public List<String> searchAppName(String name) {
        String sql = "select * from `LFGservice`.`lfg_app_list` WHERE app_name Like ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,"%"+name+"%");
            rs = pstmt.executeQuery();
            List<String> apps = new ArrayList<>();
            while (rs.next()){
                apps.add(rs.getString("app_name"));
            }
            return apps;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Doc searchAppId(Doc doc) {
        String sql = "select app_id from `LFGservice`.`lfg_app_list` WHERE app_name=?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,doc.getDoc_app_name());
            rs = pstmt.executeQuery();
            while (rs.next()){
                doc.setDoc_app_id(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return doc;
    }

    @Override
    public void deleteField() {
        String sql = "TRUNCATE TABLE LFG_COLONY.lfg_app_list";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
            logger.info("app_list갱신중 deleteField() 종료.");
        } catch (SQLException e) {
            logger.warn("app_list갱신중 deleteField() 실패. :"+e.getMessage());
            throw new RuntimeException(e);
        }finally {
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
