package com.thucnh96.jpa.service;

import com.thucnh96.jpa.constants.ConnectionType;

public class SqlServerOrm implements Orm {
    @Override
    public String getOrmName() {
        return ConnectionType.SQLSERVER.toString();
    }

    @Override
    public void gen() {

    }
}
