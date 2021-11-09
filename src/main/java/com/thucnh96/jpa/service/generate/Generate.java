package com.thucnh96.jpa.service.generate;

import com.thucnh96.jpa.modal.Table;
import com.thucnh96.jpa.modal.payload.ProjectIto;
import com.thucnh96.jpa.service.PushMessageService;

import java.util.List;

public interface Generate {
    void gen(List<Table> tables, ProjectIto project, PushMessageService pushMessageService) throws Exception;
    String getOrmName();
}
