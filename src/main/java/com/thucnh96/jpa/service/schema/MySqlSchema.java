package com.thucnh96.jpa.service.schema;

import com.thucnh96.jpa.constants.JpaConstants;
import com.thucnh96.jpa.modal.Column;
import com.thucnh96.jpa.modal.Table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MySqlSchema implements Schema {

    private  Connection connection;

    public MySqlSchema( Connection connection){
        this.connection = connection;
    }

    @Override
    public List<Table> getTables(String tabalePrefix) throws Exception {
        List<Table> tables = new ArrayList<>();
        try {
            String query = JpaConstants.mysqlQueryTable;
            PreparedStatement st = connection.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            while (rs.next())
            {
                tables.add(new Table(rs.getString(1),new ArrayList<>()));
            }
            for (Table table : tables){
                String queryColums = JpaConstants.mysqlQueryColums.concat(table.getName().replace("\"",""));
                PreparedStatement stColums = connection.prepareStatement(queryColums);
                ResultSet rsColums = stColums.executeQuery();
                while (rsColums.next())
                {
                    String fieldName = rsColums.getString("Field");
                    String dataType = rsColums.getString("Type");
                    String isNull = rsColums.getString("Null");
                    String key = rsColums.getString("Key");
                    String defaultValue = rsColums.getString("Default");
                    String extra = rsColums.getString("Extra");
                    Column colum = new Column(fieldName,dataType,isNull,key,defaultValue,extra);
                    table.getColums().add(colum);
                }
            }
            rs.close();
            st.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (connection != null){
                connection.close();
            }
        }
        return tables;
    }
}
