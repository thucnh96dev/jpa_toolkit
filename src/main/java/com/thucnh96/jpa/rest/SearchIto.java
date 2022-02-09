package com.thucnh96.jpa.rest;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : thucnh
 * @mailto : thucnh96.dev@gmail.com
 * @created :03/12/2021 - 11:35 AM
 */
@Data
@NoArgsConstructor
public class SearchIto {
    private int current = 1;
    private int pageSize = 10;
    private String[] sort;
}
