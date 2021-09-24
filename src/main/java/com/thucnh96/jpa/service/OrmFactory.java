package com.thucnh96.jpa.service;

import com.thucnh96.jpa.constants.ConnectionType;

/**
 * author thucnh
 */
public class OrmFactory {

    // private
    private OrmFactory(){
    }

    /**
     *
     * @param connectionType
     * @return Orm
     * @throws Exception
     */
    public static Orm getOrm(ConnectionType connectionType) throws Exception {
        switch(connectionType){
            case MYSQL:
                return  new MySqlOrm();
            case POSTGRES:
                return  new PostGressOrm();
            case ORACLE:
                return  new OracleOrm();
            case SQLSERVER:
                return  new SqlServerOrm();
            default:
                throw new Exception("No mapping ORM");
        }
    }
}
