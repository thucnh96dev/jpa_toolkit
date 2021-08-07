package com.thucnh96.jpa.constants;

public class JpaConstants {

    public  static  enum DbType {
        MYSQL,POSTGRES,ORACLE,SQLSERVER
    }

    public static  String mysqlQueryTable = "SHOW TABLES;";
    public static  String postgrsQueryTable = "SELECT * FROM information_schema.tables  WHERE table_schema = 'public'";
    public static  String oracleQueryTable = "";
    public static  String sqlserverQueryTable = "";

    public static  String mysqlQueryColums = "SHOW COLUMNS FROM ;";
    public static  String postgrsQueryColums = "SELECT * FROM information_schema.columns WHERE table_name  = ";
    public static  String oracleQueryColums = "";
    public static  String sqlserverQueryColums = "";

}
