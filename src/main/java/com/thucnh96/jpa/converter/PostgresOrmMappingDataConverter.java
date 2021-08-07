package com.thucnh96.jpa.converter;

/**
 * 
 * @author thucnh
 *
 */
public class PostgresOrmMappingDataConverter extends AbstractOrmMappingDataConverter {


	/**
	 * param columnDataType;
	 * return String
	 */
	@Override
	public String convertToJavaDataType(String columnDataType) {
		  String result = null;
          switch (columnDataType) {
              case "int8" :
                  result = "Long" ;
                  break;
              case "int2" :
                  result = "Integer" ;
                  break;
              case "int" :
                  result = "Integer" ;
                  break;
              case "varchar":
                  result = "String" ;
                  break;
              case  "bigint":
                  result = "Long" ;
                  break;
              case "tinyint":
                  result = "String" ;
                  break;
              case "date" :
                  result = "Date" ;
                  break;
              case "longtext" :
                  result = "String" ;
                  break;
              case "enum" :
                  result = "String" ;
                  break;
              case "json" :
                  result = "String" ;
                  break;
              case "text" :
                  result = "String" ;
                  break;
              case "mediumtext" :
                  result = "String" ;
                  break;
              case "timestamp" :
                  result = "Date" ;
                  break;
              case "datetime" :
                  result = "Date" ;
                  break;
              case "set" :
                  result = "String" ;
                  break;
              case "binary" :
                  result = "byte[]" ;
                  break;
              case "char" :
                  result = "String" ;
                  break;
              case "float" :
                  result = "Float" ;
                  break;
              case "varbinary" :
                  result = "String" ;
                  break;
              case "decimal" :
                  result = "BigDecimal" ;
                  break;
              case "blob" :
                  result = "byte[]" ;
                  break;
              case "double" :
                  result = "Double" ;
                  break;
              case "mediumblob" :
                  result = "byte[]" ;
                  break;
              case "smallint" :
                  result = "Short" ;
                  break;
              case "time" :
                  result = "Date" ;
                  break;
              case "longblob" :
                  result = "byte[]" ;
                  break;
              case "geometry" :
                  result = "byte[]" ;
                  break;
              case "year" :
                  result = "Short" ;
                  break;
              case "mediumint" :
                  result = "Long" ;
                  break;
              default:
                  result = "String" ;
                  break;
          }
      return result;
	}

}
