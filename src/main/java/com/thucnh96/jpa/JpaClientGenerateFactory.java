package com.thucnh96.jpa;

import com.thucnh96.jpa.converter.MysqlOrmMappingConverter;
import com.thucnh96.jpa.converter.PostgresOrmMappingConverter;
import com.thucnh96.jpa.modal.DbType;
import com.thucnh96.jpa.service.generate.Generate;
import com.thucnh96.jpa.service.generate.MySqlGenerate;
import com.thucnh96.jpa.service.generate.PostGressGenerate;

/**
 * author thucnh
 */
public class JpaClientGenerateFactory {

    // private
    private JpaClientGenerateFactory(){
    }

    /**
     *
     * @param connectionType
     * @return Orm
     * @throws Exception
     */
    public static Generate getGenerate(DbType connectionType) throws Exception {
        switch(connectionType){
            case MYSQL:
                return  new MySqlGenerate(new MysqlOrmMappingConverter());
            case POSTGRES:
                return  new PostGressGenerate(new PostgresOrmMappingConverter());
            default:
                throw new Exception("No mapping ORM");
        }
    }
}
