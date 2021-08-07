package com.thucnh96.jpa.service.orm;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.thucnh96.jpa.connector.DataSourceConnector;
import com.thucnh96.jpa.converter.AbstractOrmMappingDataConverter;
import com.thucnh96.jpa.modal.Table;
import com.thucnh96.jpa.service.PushMessageService;
import com.thucnh96.jpa.service.mapping.MysqlSchema;
import com.thucnh96.jpa.service.mapping.PostgresSchema;

/**
 * 
 * @author thucnh
 *
 */
public class PostgresOrmGenerate extends AbstractOrmGenerate {
	
	List<Table> tables;
	String entityFolder;
	String serviceFolder;
	String repositoryFolder;
	String controllerFolder;
	String projectPackage;
	String dtoFolder;
	String specFolder;
	String urlDataSource;
    String userName;
    String passWord;
	AbstractOrmMappingDataConverter abstractOrmMappingDataConverter;

	public PostgresOrmGenerate(List<Table> tables, String path, String entityFolder, String serviceFolder,
			String repositoryFolder, String controllerFolder, String projectPackage, String dtoFolder,
			String specFolder, String urlDataSource, String userName,  String passWord,
			PushMessageService pushMessageService,AbstractOrmMappingDataConverter abstractOrmMappingDataConverter) {
		super(path,abstractOrmMappingDataConverter, pushMessageService);
		this.tables = tables;
		this.entityFolder = entityFolder;
		this.serviceFolder = serviceFolder;
		this.repositoryFolder = repositoryFolder;
		this.controllerFolder = controllerFolder;
		this.projectPackage = projectPackage;
		this.dtoFolder = dtoFolder;
		this.specFolder = specFolder;
		this.urlDataSource = urlDataSource;
		this.userName = userName;
		this.passWord = passWord;
	}
	
	public void genProgress() throws IOException, SQLException {
		Connection connection = DataSourceConnector.getConnection(urlDataSource,userName,passWord);
		PostgresSchema mysqlSchema = new PostgresSchema(connection);
		this.tables = mysqlSchema.getTables();
	        for (Table table : tables){
	            this.bodyItoAndDto(table,this.projectPackage,this.dtoFolder);
	            this.bodyEntity(table,this.projectPackage,this.entityFolder);
	            this.bodyRepository(table,this.projectPackage,this.repositoryFolder,this.entityFolder);
	            this.bodySpec(table,this.projectPackage,this.entityFolder,this.specFolder);
	            this.bodyService(table,this.projectPackage,this.serviceFolder,this.entityFolder,this.dtoFolder);
	            this.bodyServiceImpl(table,this.projectPackage,this.serviceFolder,this.repositoryFolder,this.entityFolder,this.dtoFolder,this.specFolder);
	            this.bodyController(table,this.projectPackage,this.controllerFolder,this.serviceFolder,this.entityFolder,this.dtoFolder);
	        }
	 }

}
