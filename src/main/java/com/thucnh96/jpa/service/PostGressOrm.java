package com.thucnh96.jpa.service;

import com.thucnh96.jpa.constants.ConnectionType;

public class PostGressOrm implements Orm{
    @Override
    public String getOrmName() {
        return ConnectionType.POSTGRES.toString();
    }

    @Override
    public void gen() {

    }
}
