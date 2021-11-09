package com.thucnh96.jpa;

import com.thucnh96.jpa.modal.DbType;
import com.thucnh96.jpa.modal.payload.ProjectIto;
import com.thucnh96.jpa.service.schema.MySqlSchema;
import com.thucnh96.jpa.service.schema.PostgresSchema;
import com.thucnh96.jpa.service.schema.Schema;


public class JpaClientSchemaFactory {
    // private
    private JpaClientSchemaFactory(){
    }
    /**
     *
     * @param dbType
     * @return Orm
     * @throws Exception
     */
    public static Schema getSchema(DbType dbType , ProjectIto projectIto) throws Exception {
        switch(dbType){
            case MYSQL:
                return  new MySqlSchema(projectIto);
            case POSTGRES:
                return  new PostgresSchema(projectIto);
            default:
                throw new Exception("No mapping ORM");
        }
    }
}
