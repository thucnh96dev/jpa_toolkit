package com.thucnh96.jpa.modal;

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
public class Column {
	
	private String fieldName;
	private String dataType;
    private String isNull;
    private String key;
    private String defaultValue;
    private String extra;

}
