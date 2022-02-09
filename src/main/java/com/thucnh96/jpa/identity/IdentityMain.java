package com.thucnh96.jpa.identity;

import com.thucnh96.jpa.connector.DataSourceConnector;
import com.thucnh96.jpa.constants.JpaConstants;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author : thucnh
 * @mailto : thucnh96.dev@gmail.com
 * @created :06/01/2022 - 1:56 PM
 */
public class IdentityMain {

    public static void main(String[] args) throws SQLException {
        Connection connection = DataSourceConnector.getConnection("jdbc:postgresql://databasedomain:5432/module_kehoach_taichinh","postgres","123");
        String query = JpaConstants.postgrsQueryTable;
        PreparedStatement st = connection.prepareStatement(query);
        ResultSet rs = st.executeQuery();
        while (rs.next())
        {
            if (!rs.getString("table_name").startsWith("qrtz")){
                try{
                  String autoSql =  prinSeg(rs.getString("table_name"));
                  PreparedStatement stupdate = connection.prepareStatement(autoSql);
                  stupdate.executeUpdate();
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
            }
        }
    }
    public static  String prinSeg(String tableName){
        String srt = "create sequence "+tableName+"_id_seq;\n" +
                "\n" +
                "alter table "+tableName+" alter column id set default nextval('public."+tableName+"_id_seq'::regclass);\n" +
                "\n" +
                "alter sequence "+tableName+"_id_seq owned by "+tableName+".id; \n";
        System.out.println(srt);
        return srt;
    }
}
