package com.thucnh96.jpa.service.orm;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.List;

import com.thucnh96.jpa.component.CommonSource;
import com.thucnh96.jpa.connector.DataSourceConnector;
import com.thucnh96.jpa.constants.JpaConstants;
import com.thucnh96.jpa.converter.AbstractOrmMappingDataConverter;
import com.thucnh96.jpa.modal.Table;
import com.thucnh96.jpa.modal.payload.ProjectIto;
import com.thucnh96.jpa.service.PushMessageService;
import com.thucnh96.jpa.service.doc.GenDocument;
import com.thucnh96.jpa.service.schema.Schema;
import com.thucnh96.jpa.service.schema.SchemaFactory;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * 
 * @author thucnh
 *
 */
public class PostgresOrmGenerate extends AbstractOrmGenerate {

	private ProjectIto project;

	public PostgresOrmGenerate(
			ProjectIto project,
			PushMessageService pushMessageService,
			AbstractOrmMappingDataConverter abstractOrmMappingDataConverter) {
		super(project.getPath(),abstractOrmMappingDataConverter, pushMessageService);
		this.project = project;

	}
	
	public void genProgress() throws Exception {
		Connection connection = DataSourceConnector.getConnection(project.getUrl(),project.getUsername(),project.getPassword());
		Schema postgresSchema = SchemaFactory.getSchema(JpaConstants.DbType.POSTGRES,connection);
		List<Table>	tables = postgresSchema.getTables();
		for (Table table : tables){
			this.bodyItoAndDto(table,this.project.getPackages(),this.project.getDtoFolder());
			this.bodyEntity(table,this.project.getPackages(),this.project.getEntityFolder());
			this.bodyRepository(table,this.project.getPackages(),this.project.getRepositoryFolder(),this.project.getEntityFolder());
			this.bodySpec(table,this.project.getPackages(),this.project.getEntityFolder(),this.project.getSepcFolder());
			this.bodyService(table,this.project.getPackages(),this.project.getServiceFolder(),this.project.getEntityFolder(),this.project.getDtoFolder());
			this.bodyServiceImpl(table,this.project.getPackages(),this.project.getServiceFolder(),this.project.getRepositoryFolder(),this.project.getEntityFolder(),this.project.getDtoFolder(),this.project.getSepcFolder());
			this.bodyController(table,this.project.getPackages(),this.project.getRestFolder(),this.project.getServiceFolder(),this.project.getEntityFolder(),this.project.getDtoFolder());
		}
		CommonSource commonSource = new CommonSource(this.path,this.project.getPackages());
		commonSource.run();
		if (project.isGenDoc()){
			try {
				Workbook workbook = new GenDocument(tables).genDoc();
				String fullPatchFile = this.path.concat("/").concat("document").concat("/").concat("database").concat(".xlsx");
				String dirFolder = Paths.get(this.path, "document").toString();
				File fileDir = new File(dirFolder);
				if (!fileDir.exists()) {
					fileDir.mkdirs();
				}
				File file = new File(fullPatchFile);
				FileOutputStream outFile = new FileOutputStream(file);
				workbook.write(outFile);
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	 }

}
