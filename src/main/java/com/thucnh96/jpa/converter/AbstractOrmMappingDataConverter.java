package com.thucnh96.jpa.converter;

/**
 * 
 * @author thucnh
 *
 */
public abstract class AbstractOrmMappingDataConverter {
	
	/**
	 * 
	 * @param name
	 * @param newName
	 */
	public void  renameColumn (String name,String newName){
		name = newName;
	}	
	/**
	 * 
	 * @param columnDataType
	 * @return
	 */
	public abstract String convertToJavaDataType(String columnDataType);

}
