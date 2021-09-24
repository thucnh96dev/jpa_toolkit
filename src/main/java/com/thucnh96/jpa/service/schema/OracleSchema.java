package com.thucnh96.jpa.service.schema;

import com.thucnh96.jpa.constants.ConnectionType;
import com.thucnh96.jpa.modal.Table;
import com.thucnh96.jpa.service.Orm;

import java.util.List;

public class OracleSchema implements Schema {

    @Override
    public List<Table> getTables() {
        return null;
    }
}
