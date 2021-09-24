package com.thucnh96.jpa.service;

import com.thucnh96.jpa.constants.ConnectionType;

public class MySqlOrm implements Orm  {
    @Override
    public String getOrmName() {
          return ConnectionType.MYSQL.toString();
    }

    @Override
    public void gen() {

    }
}
