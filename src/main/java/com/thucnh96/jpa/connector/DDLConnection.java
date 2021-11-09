package com.thucnh96.jpa.connector;

import java.sql.*;

public class DDLConnection {

	private DDLConnection(){
	}
	
	public static Connection getConnection(String url,String userName,String passWord) throws SQLException {
		Connection connection = DriverManager.getConnection(url,userName,passWord);
		if (null != connection){
			return connection;
		}
		return  null;
	}

	public static void closeConnection(Connection connection) throws SQLException {
		if ( null != connection){
			connection.close();
		}
	}

	public static void closePreparedStatement(PreparedStatement preparedStatement) throws SQLException {
		if ( null != preparedStatement){
			preparedStatement.close();
		}
	}
	public  static void closeResultSet(ResultSet resultSet) throws SQLException {
		if ( null != resultSet){
			resultSet.close();
		}
	}

}
