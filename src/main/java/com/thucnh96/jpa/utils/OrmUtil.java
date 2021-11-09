package com.thucnh96.jpa.utils;

import com.thucnh96.jpa.modal.DbType;

public class OrmUtil {
    /**
     *
     * @param input
     * @return ConnectionType
     */
    public static DbType convertToOrm(String input) throws Exception {
        DbType data = DbType.valueOf(input);
        return data;
    }

}
