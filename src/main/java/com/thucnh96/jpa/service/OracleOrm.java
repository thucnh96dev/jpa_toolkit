package com.thucnh96.jpa.service;

import com.thucnh96.jpa.constants.ConnectionType;
import com.thucnh96.jpa.modal.payload.ProjectIto;

public class OracleOrm implements Orm {
    @Override
    public String getOrmName() {
        return ConnectionType.ORACLE.toString();
    }

    @Override
    public void gen(ProjectIto projectIto,PushMessageService pushMessageService) {

    }
}
