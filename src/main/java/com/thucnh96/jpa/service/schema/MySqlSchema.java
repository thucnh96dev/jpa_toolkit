package com.thucnh96.jpa.service.schema;

import com.thucnh96.jpa.connector.DDLConnection;
import com.thucnh96.jpa.constants.JpaConstants;
import com.thucnh96.jpa.modal.Column;
import com.thucnh96.jpa.modal.DbType;
import com.thucnh96.jpa.modal.Table;
import com.thucnh96.jpa.modal.payload.ProjectIto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MySqlSchema implements Schema {

    private ProjectIto projectIto;
    public MySqlSchema( ProjectIto projectIto){
        this.projectIto = projectIto;
    }

    @Override
    public List<Table> getTables() throws Exception {
        List<Table> tables = new ArrayList<>();
        Connection connection = null;
        try {
            connection = DDLConnection.getConnection(projectIto.getUrl(),projectIto.getUsername(),projectIto.getPassword());
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
            DDLConnection.closeResultSet(rs);
            DDLConnection.closePreparedStatement(st);
            DDLConnection.closeConnection(connection);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (connection != null){
                DDLConnection.closeConnection(connection);
            }
        }
        return tables;
    }

    @Override
    public String getOrmName() {
        return DbType.MYSQL.name();
    }
}
