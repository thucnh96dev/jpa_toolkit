package com.thucnh96.jpa.service.schema;

import com.thucnh96.jpa.modal.Table;

import java.util.List;

public interface Schema {
    List<Table> getTables() throws Exception;
    String getOrmName();
}
