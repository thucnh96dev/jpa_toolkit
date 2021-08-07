package com.thucnh96.jpa.connector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSourceConnector {
	
	   public static Connection getConnection(String url,String userName,String passWord) throws SQLException {
	        Connection connection = DriverManager.getConnection(url,userName,passWord);
	        if (null != connection){
	            return connection;
	        }
	        return  null;
	    }

}
