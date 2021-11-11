package com.thucnh96.jpa.connector;

import com.thucnh96.jpa.exeption.JpaApiExeption;

import java.sql.*;

public class DDLConnection {

	private DDLConnection(){
	}
	
	public static Connection getConnection(String url,String userName,String passWord)  {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(url,userName,passWord);
			return connection;
		} catch (SQLException e) {
			throw  new JpaApiExeption(e);
		}
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
