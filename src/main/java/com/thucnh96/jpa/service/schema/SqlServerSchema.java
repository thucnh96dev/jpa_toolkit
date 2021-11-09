package com.thucnh96.jpa.service.schema;

import com.thucnh96.jpa.modal.Table;

import java.util.List;

/**
 * @author : thucnh
 * @mailto : thucnh96.dev@gmail.com
 * @created :09/11/2021 - 10:01 AM
 */
public class SqlServerSchema implements Schema{
    @Override
    public List<Table> getTables() throws Exception {
        return null;
    }

    @Override
    public String getOrmName() {
        return null;
    }
}
