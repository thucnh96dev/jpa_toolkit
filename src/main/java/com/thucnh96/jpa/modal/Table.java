package com.thucnh96.jpa.modal;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
/**
 * 
 * @author thucnh
 *
 */
public class Table {
	private String name;
    private List<Column> colums ;
}
