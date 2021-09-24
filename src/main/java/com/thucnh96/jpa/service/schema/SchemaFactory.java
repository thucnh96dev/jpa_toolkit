package com.thucnh96.jpa.service.schema;

import com.thucnh96.jpa.constants.JpaConstants;

import java.sql.Connection;

public class SchemaFactory {
    // private
    private SchemaFactory(){
    }
    /**
     *
     * @param connectionType
     * @return Orm
     * @throws Exception
     */
    public static Schema getSchema(JpaConstants.DbType connectionType, Connection connection) throws Exception {
        switch(connectionType){
            case MYSQL:
                return  new MySqlSchema(connection);
            case POSTGRES:
                return  new PostgresSchema(connection);
            default:
                throw new Exception("No mapping ORM");
        }
    }
}
