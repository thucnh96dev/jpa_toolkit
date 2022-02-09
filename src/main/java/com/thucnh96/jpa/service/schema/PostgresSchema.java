package com.thucnh96.jpa.service.schema;

import com.thucnh96.jpa.constants.JpaConstants;
import com.thucnh96.jpa.modal.Column;
import com.thucnh96.jpa.modal.Table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * author: thucnh
 */
public class PostgresSchema implements Schema {

    private  Connection connection;

    public PostgresSchema(Connection connection){
        this.connection = connection;
    }

    @Override
    public List<Table> getTables() throws Exception {
        List<Table> tables = new ArrayList<>();
        try {
            String query = JpaConstants.postgrsQueryTable;
            PreparedStatement st = connection.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            while (rs.next())
            {
                if (rs.getString("table_name").startsWith("doc_bieumau_col")){
                tables.add(new Table(rs.getString("table_name"),new ArrayList<>()));
                }
            }
            for (Table table : tables){
                    String queryColums = JpaConstants.postgrsQueryColums.concat("\'").concat(table.getName().replace("\"","")).concat("\'");
                    PreparedStatement stColums = connection.prepareStatement(queryColums);
                    ResultSet rsColums = stColums.executeQuery();
                    while (rsColums.next())
                    {
                        String fieldName = rsColums.getString("column_name");
                        String dataType = rsColums.getString("udt_name");
                        String isNull = rsColums.getString("is_nullable");
                        String key = rsColums.getString("table_schema");
                        String defaultValue = rsColums.getString("column_default");
                        String extra = rsColums.getString("table_schema");
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
