package com.thucnh96.jpa.service.generate;

import com.thucnh96.jpa.constants.AppConstants;
import com.thucnh96.jpa.converter.AbstractOrmMappingConverter;
import com.thucnh96.jpa.modal.DbType;
import com.thucnh96.jpa.modal.Table;
import com.thucnh96.jpa.modal.payload.ProjectIto;
import com.thucnh96.jpa.service.AbstractGenerate;
import com.thucnh96.jpa.service.PushMessageService;
import com.thucnh96.jpa.utils.ExcuteFile;
import com.thucnh96.jpa.utils.SchemaUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.StringWriter;
import java.util.List;
import java.util.Map;

public class PostGressGenerate extends AbstractGenerate implements Generate{


    public PostGressGenerate(AbstractOrmMappingConverter abstractOrmMappingConverter) {
        super(abstractOrmMappingConverter);
    }

    @Override
    public void gen(List<Table> tables, ProjectIto project, PushMessageService pushMessageService) throws Exception {

        Map<AppConstants.PATCH, String> folders = this.getPackeges(project);

        String entityPackage =  project.getPackages().concat(".").concat(project.getEntityFolder());
        String servicePackage =  project.getPackages().concat(".").concat(project.getServiceFolder());
        String resourcePackage =  project.getPackages().concat(".").concat(project.getRestFolder());
        String specPackage =  project.getPackages().concat(".").concat(project.getSepcFolder());
        String daoPackage =  project.getPackages().concat(".").concat(project.getRepositoryFolder());
        String base_toPackage =  project.getPackages().concat(".").concat(project.getDtoFolder());

        String author = project.getAuthor();

        VelocityContext contextCommon = new VelocityContext();
        contextCommon.put("projectAUthor", entityPackage);
        contextCommon.put("entityPackage",entityPackage );
        contextCommon.put("servicePackage",servicePackage );
        contextCommon.put("resourcePackage", resourcePackage );
        contextCommon.put("specPackage", specPackage );
        contextCommon.put("daoPackage",daoPackage);
        contextCommon.put("base_toPackage", base_toPackage);

        for (Table table: tables ) {

            String entityName = SchemaUtils.entityName(table.getName());
            String rawEntity = this.rawEntity();
            String rawDao= this.rawDao();
            String rawDto= this.rawDto();
            String rawIto= this.rawIto();
            String rawService = this.rawService();
            String rawServiceImpl = this.rawServiceImpl();
            String rawSpec = this.rawSpec();

            String entityBody = this.rawFieldName(table);

            VelocityContext context = (VelocityContext) contextCommon.clone();
            context.put("entityName", entityName);
            context.put("tableName", table.getName());
            context.put("entityBody", entityBody);
            context.put("package", "Jakarta");
            context.put("daoName", entityName.concat(AppConstants.DAO_SUBFIX));
            /* lets render a template */
            /* lets make our own string to render */
            StringWriter writerEntity = new StringWriter();
            StringWriter writerDao = new StringWriter();
            StringWriter writerDto = new StringWriter();
            StringWriter writerIto = new StringWriter();
            StringWriter writerService= new StringWriter();
            Velocity.evaluate( context, writerEntity, "rawEntity", rawEntity );
            Velocity.evaluate( context, writerDao, "rawDao", rawDao );

            ExcuteFile.createFile(project.getPath(),project.getEntityFolder(),SchemaUtils.entityName(table.getName()),writerEntity.toString(),pushMessageService);
            ExcuteFile.createFile(project.getPath(),project.getRepositoryFolder(),SchemaUtils.entityName(table.getName()).concat(AppConstants.DAO_SUBFIX),writerDao.toString(),pushMessageService);
        }

    }

    @Override
    public String getOrmName() {
        return DbType.POSTGRES.name();
    }
}
