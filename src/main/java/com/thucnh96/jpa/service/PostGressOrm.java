package com.thucnh96.jpa.service;

import com.thucnh96.jpa.constants.ConnectionType;
import com.thucnh96.jpa.converter.PostgresOrmMappingDataConverter;
import com.thucnh96.jpa.modal.payload.ProjectIto;
import com.thucnh96.jpa.service.orm.AbstractOrmGenerate;
import com.thucnh96.jpa.service.orm.PostgresOrmGenerate;

public class PostGressOrm implements Orm{

    @Override
    public String getOrmName() {
        return ConnectionType.POSTGRES.toString();
    }

    @Override
    public void gen(ProjectIto projectIto,PushMessageService pushMessageService) throws Exception {
        AbstractOrmGenerate abstractOrmGenerate = new PostgresOrmGenerate(projectIto,pushMessageService, new PostgresOrmMappingDataConverter());
        abstractOrmGenerate.genProgress();
    }
}
