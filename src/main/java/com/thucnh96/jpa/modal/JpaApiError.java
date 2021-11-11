package com.thucnh96.jpa.modal;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @author : thucnh
 * @mailto : thucnh96.dev@gmail.com
 * @created :11/11/2021 - 1:19 PM
 */
@Data
@NoArgsConstructor
@Builder
public class JpaApiError {

    /**
     * Error code.
     */
    private int code;

    /**
     * Error message.
     */
    private String msg;

    /**
     * Error timestamp
     */
    private Timestamp timestamp;

}
