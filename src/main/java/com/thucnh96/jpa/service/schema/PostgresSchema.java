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

/**
 * author: thucnh
 */
public class PostgresSchema implements Schema {

    private ProjectIto projectIto;
    public PostgresSchema( ProjectIto projectIto){
        this.projectIto = projectIto;
    }
    @Override
    public List<Table> getTables() throws Exception {
        List<Table> tables = new ArrayList<>();
        Connection connection = null;
        try {
            connection = DDLConnection.getConnection(projectIto.getUrl(),projectIto.getUsername(),projectIto.getPassword());
            String query = JpaConstants.postgrsQueryTable;
            PreparedStatement st = connection.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            while (rs.next())
            {
                 tables.add(new Table(rs.getString("table_name"), new ArrayList<>()));
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
        return DbType.POSTGRES.name();
    }
}
