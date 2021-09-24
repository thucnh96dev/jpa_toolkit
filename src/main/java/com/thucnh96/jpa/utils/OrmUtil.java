package com.thucnh96.jpa.utils;

import com.thucnh96.jpa.constants.ConnectionType;

public class OrmUtil {
    /**
     *
     * @param input
     * @return ConnectionType
     */
    public static ConnectionType convertToOrm(String input) throws Exception {
        ConnectionType data = ConnectionType.valueOf(input);
        return data;

    }

}
