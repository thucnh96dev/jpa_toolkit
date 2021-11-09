package com.thucnh96.jpa.service.generate;

import com.thucnh96.jpa.converter.AbstractOrmMappingConverter;
import com.thucnh96.jpa.modal.DbType;
import com.thucnh96.jpa.modal.Table;
import com.thucnh96.jpa.modal.payload.ProjectIto;
import com.thucnh96.jpa.service.AbstractGenerate;
import com.thucnh96.jpa.service.PushMessageService;

import java.util.List;

public class SqlServerGenerate extends AbstractGenerate implements Generate {

    public SqlServerGenerate(AbstractOrmMappingConverter abstractOrmMappingConverter) {
        super(abstractOrmMappingConverter);
    }

    @Override
    public void gen(List<Table> tables, ProjectIto project, PushMessageService pushMessageService) throws Exception {



    }

    @Override
    public String getOrmName() {
        return DbType.SQLSERVER.name();
    }
}
