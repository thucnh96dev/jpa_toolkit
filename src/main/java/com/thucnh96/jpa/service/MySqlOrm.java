package com.thucnh96.jpa.service;

import com.thucnh96.jpa.constants.ConnectionType;
import com.thucnh96.jpa.converter.MysqlOrmMappingDataConverter;
import com.thucnh96.jpa.modal.payload.ProjectIto;
import com.thucnh96.jpa.service.orm.AbstractOrmGenerate;
import com.thucnh96.jpa.service.orm.MysqlOrmGenerate;

public class MySqlOrm implements Orm  {
    @Override
    public String getOrmName() {
          return ConnectionType.MYSQL.toString();
    }

    @Override
    public void gen(ProjectIto projectIto,PushMessageService pushMessageService) throws Exception {
        AbstractOrmGenerate abstractOrmGenerate = new MysqlOrmGenerate(projectIto,pushMessageService, new MysqlOrmMappingDataConverter());
        abstractOrmGenerate.genProgress();
    }
}
