package com.thucnh96.jpa.service;

import com.thucnh96.jpa.modal.payload.ProjectIto;

public interface Orm {
    String getOrmName();
    void gen(ProjectIto projectIto,PushMessageService pushMessageService) throws Exception;

}
