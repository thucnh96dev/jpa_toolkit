package com.thucnh96.jpa.service.schema;

import com.thucnh96.jpa.modal.DbType;
import com.thucnh96.jpa.modal.Table;

import java.util.List;

public class OracleSchema implements Schema {

    @Override
    public List<Table> getTables() {
        return null;
    }

    @Override
    public String getOrmName() {
        return DbType.ORACLE.name();
    }
}
