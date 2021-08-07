package com.thucnh96.jpa.service.orm;

import java.util.List;

import com.thucnh96.jpa.converter.AbstractOrmMappingDataConverter;
import com.thucnh96.jpa.modal.Table;
import com.thucnh96.jpa.service.PushMessageService;

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
	AbstractOrmMappingDataConverter abstractOrmMappingDataConverter;

	public PostgresOrmGenerate(List<Table> tables, String path, String entityFolder, String serviceFolder,
			String repositoryFolder, String controllerFolder, String projectPackage, String dtoFolder,
			String specFolder, PushMessageService pushMessageService,AbstractOrmMappingDataConverter abstractOrmMappingDataConverter) {
		super(path,abstractOrmMappingDataConverter, pushMessageService);
		this.tables = tables;
		this.entityFolder = entityFolder;
		this.serviceFolder = serviceFolder;
		this.repositoryFolder = repositoryFolder;
		this.controllerFolder = controllerFolder;
		this.projectPackage = projectPackage;
		this.dtoFolder = dtoFolder;
		this.specFolder = specFolder;
	}

}
