package com.hcf.nszh.provider.mz.utils;

import com.hcf.nszh.provider.mz.config.JdbcConfig;
import com.hcf.nszh.provider.mz.entity.JdbcEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈数据库增删查改〉
 *
 * @author maruko
 * @date 2021/7/6 16:50
 * @since 1.0.0
 */
@Slf4j
public class JdbcUtil {


    /**
     * 执行SQL
     *
     * @param connection
     * @param sql
     * @param params
     * @throws Exception
     */
    public static PreparedStatement prepareStatement(Connection connection, String sql, List<Object> params) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        if (CollectionUtils.isNotEmpty(params)) {
            for (int i = 0; i < params.size(); i++) {
                preparedStatement.setObject(i + 1, params.get(i));
            }
        }
        return preparedStatement;
    }

    /**
     * 条件查询
     *
     * @param jdbcEntity
     * @param sql
     * @param params
     * @return
     */
    public static List<Map<String, Object>> executeQuery(JdbcEntity jdbcEntity, String sql,
                                                         List<Object> params) {
        List<Map<String, Object>> list = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = JdbcConfig.getConnection(jdbcEntity);
            preparedStatement = prepareStatement(connection, sql, params);
            resultSet = preparedStatement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int colsLen = metaData.getColumnCount();
            while (resultSet.next()) {
                Map<String, Object> map = new HashMap<>();
                for (int i = 0; i < colsLen; i++) {
                    String colsName = metaData.getColumnName(i + 1);
                    Object colsValue = resultSet.getObject(colsName);
                    if (colsValue == null) {
                        colsValue = "";
                    }
                    map.put(colsName, colsValue);
                }
                list.add(map);
            }
            return list;
        } catch (SQLException e) {
            log.error("", e);
        } finally {
            close(resultSet, preparedStatement, connection);
        }
        return null;
    }

    /**
     * 条件插入
     *
     * @param jdbcEntity
     * @param sql
     * @param params
     * @return
     */
    public static int executeInsert(JdbcEntity jdbcEntity, String sql,
                                    List<Object> params) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = JdbcConfig.getConnection(jdbcEntity);
            preparedStatement = prepareStatement(connection, sql, params);
            //设置自动提交为true
            connection.setAutoCommit(true);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            close(resultSet, preparedStatement, connection);
        }
    }

    /**
     * 释放资源
     *
     * @param rs
     * @param st
     * @param conn
     */
    public static void close(ResultSet rs, Statement st, Connection conn) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                log.error("sql执行异常", e);
            }
        }
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                log.error("sql执行异常", e);
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                log.error("数据库关闭异常", e);
            }
        }
    }

    public static void main(String[] args) {
//        JdbcEntity jdbcEntity1 = new JdbcEntity("MySQL", "jdbc:mysql://localhost:3307/courttax?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8&allowMultiQueries=true&useSSL=false",
//                "root", "123456");

        JdbcEntity jdbcEntity2 = new JdbcEntity("Oracle", "jdbc:oracle:thin:@172.16.121.219:1521:ORCL",
                "nfdp", "123456");



        String sql1 = "INSERT INTO \"test2\"(\"name\", \"age\") VALUES (?, ?)";
        List<Object> params1 = new ArrayList<>();
        params1.add("maruko");
        params1.add(18);


        String sql2 = "select * from \"test2\" where \"age\"=?";
        List<Object> params2 = new ArrayList<>();
        params2.add(18);


//        executeInsert(connection1, sql1, params1);
//        List<Map<String, Object>> mapList1 = executeQuery(connection1, sql2, params2);
//        for (Map<String, Object> objectMap : mapList1) {
//            System.out.println(objectMap);
//        }

        executeInsert(jdbcEntity2, sql1, params1);
        List<Map<String, Object>> mapList2 = executeQuery(jdbcEntity2, sql2, params2);
        for (Map<String, Object> objectMap : mapList2) {
            System.out.println(objectMap);
        }

    }
}
