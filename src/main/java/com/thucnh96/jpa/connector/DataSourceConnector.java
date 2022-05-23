package com.thucnh96.jpa.connector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataSourceConnector {

    private DataSourceConnector() {
    }

    public static Connection getConnection(String url, String userName, String passWord) throws SQLException {
        Connection connection = DriverManager.getConnection(url, userName, passWord);
        if (null != connection) {
            return connection;
        }
        return null;
    }

    public static void main(String[] args) throws SQLException {
        PreparedStatement stmt = null;
        List<Object> item = new ArrayList<>();
        String sql = "SELECT dulieuquan0_.*\n" +
                " FROM dulieuqlnsnn dulieuquan0_\n" +
                " WHERE dulieuquan0_.daxoa=0 \n" +
                "  AND dulieuquan0_.macoquan= '1012230' \n" +
                "  AND dulieuquan0_.phienban='DUTOAN'\n" +
                "  AND dulieuquan0_.nam=2022 \n" +
                "  AND dulieuquan0_.thang=0 ";
        Connection conn = getConnection("jdbc:postgresql://192.168.2.73:5432/qlnsnn_remake_dng_prod_20210617", "postgres", "123");
        try{
            long start1 = System.nanoTime();
            stmt = conn.prepareStatement(sql);
            ResultSet r = stmt.executeQuery();
            ResultSetMetaData rsmd = r.getMetaData();
            int numColumns = rsmd.getColumnCount();
            while(r.next()) {
                for (int i = 1; i < numColumns + 1; i++){
                    item.add(r.getObject(i));
                }
            }
            long end1 = System.nanoTime();

            System.out.println("time exec : "+(end1 - start1));

        }catch(Exception e){
        }
    }

}
