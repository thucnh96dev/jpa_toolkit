package com.thucnh96.jpa.service.orm;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

import com.thucnh96.jpa.constants.JpaAnotation;
import com.thucnh96.jpa.converter.AbstractOrmMappingDataConverter;
import com.thucnh96.jpa.modal.Column;
import com.thucnh96.jpa.modal.Table;
import com.thucnh96.jpa.progress.ExcuteFile;
import com.thucnh96.jpa.service.PushMessageService;

/**
 * 
 * @author thucnh
 *
 */
public abstract class AbstractOrmGenerate {
	protected String path;
    protected PushMessageService pushMessageService;
    protected AbstractOrmMappingDataConverter abstractOrmMappingDataConverter;

    public AbstractOrmGenerate(String path,AbstractOrmMappingDataConverter abstractOrmMappingDataConverter,PushMessageService pushMessageService) {
        this.path = path;
        this.pushMessageService = pushMessageService;
        this.abstractOrmMappingDataConverter = abstractOrmMappingDataConverter;
    }

    protected static String headerEntity(String packageProject, String tableName, String entityFolder){
        StringBuffer buffer = new StringBuffer();
        buffer.append(" package ").append(packageProject).append(".").append(entityFolder).append("; \n")
                .append(" import javax.persistence.*; \n")
                .append(" import java.util.*; \n")
                .append(" import lombok.*; \n")
                .append("\n")
                .append("\n")
                .append(" /**").append("\n")
                .append(" * Entity "+tbNameSp(tableName)+".").append("\n")
                .append(" * Author thucnh .").append("\n")
                .append(" * Create at "+title()+" ").append(".").append("\n")
                .append(" */ ").append("\n")
                .append(" @Data \n")
                .append(" @NoArgsConstructor \n")
                .append(" @AllArgsConstructor \n")
                .append(" @Entity \n")
                .append(" @Table(name = \"" +tableName+ "\") \n")
                .append(" public class "+tbNameSp(tableName)+" ").append("{ ")
                .append("\n").append("\n");
        return buffer.toString();
    }
    protected static String headerAllDto(String packageProject, String tableName,String type, String title,String folder){
        StringBuffer buffer = new StringBuffer();
        buffer.append(" package ").append(packageProject).append(".").append(folder).append(".").append(tbNameSp(tableName).toLowerCase()).append("; \n")
                .append(" import java.util.*; \n")
                .append(" import lombok.*; \n")
                .append("\n")
                .append("\n")
                .append(" /**")
                .append(" * "+type+" "+tbNameSp(tableName)+"").append(type).append("\n")
                .append(" * Author thucnh .").append("\n")
                .append(" * Create at "+title+" ").append("\n")
                .append(" */ ").append("\n")
                .append(" @Data \n")
                .append(" @NoArgsConstructor \n")
                .append(" @AllArgsConstructor \n")
                .append(" public class "+tbNameSp(tableName).concat(type)+"").append("{ ")
                .append("\n").append("\n");
        return buffer.toString();
    }

    protected void bodyEntity(Table table, String packageProject, String entityFolder) throws IOException {

        StringBuffer buffer = new StringBuffer();
        buffer.append(headerEntity(packageProject,table.getName(),entityFolder));
        for (Column colum: table.getColums()){
            if ("PRI".equals(colum.getKey())) { // mysql
                buffer.append("   ").append(JpaAnotation.Id).append("\n");
            }
            if (!StringUtils.isEmpty(colum.getFieldName()) && "id".equals(colum.getFieldName().toLowerCase())) { // mysql
                buffer.append("   ").append(JpaAnotation.Id).append("\n");
            }
            if (!StringUtils.isEmpty(colum.getDefaultValue()) && colum.getDefaultValue().contains("::regclass")) { // mysql
                buffer.append("   ").append(JpaAnotation.GenerationType).append("\n");
            }
            if ("auto_increment".equals(colum.getExtra())) {
                buffer.append("   ").append(JpaAnotation.GenerationType).append("\n");
            }
            buffer.append("   ").append(JpaAnotation.Column) .append("(").append("name=").append("\""+colum.getFieldName()+"\"");
            if ("UNI".equals(colum.getKey())) {
                buffer.append(",")
                        .append(JpaAnotation.unique).append("=true");
            }
            if ("text".equals(colum.getDataType())) {
                buffer.append(",")
                        .append(JpaAnotation.columnDefinition).append("=").append("\"").append("TEXT") .append("\"");
            }
            buffer.append(")").append("\n");
            buffer.append("   private ").append(" ")
                    .append(abstractOrmMappingDataConverter.convertToJavaDataType(colum.getDataType()))
                    .append(" ")
                    .append(columNameSp(colum.getFieldName())).append(" ;").append("\n").append("\n");

        }
        buffer.append("}");
    }


    protected  void bodyItoAndDto(Table table,String packageProject,String itoFolder) throws IOException {

        StringBuffer buffer = new StringBuffer();
        buffer.append(headerAllDto(packageProject,table.getName(),"Ito",title(),itoFolder));

        StringBuffer bufferDto = new StringBuffer();
        bufferDto.append(headerAllDto(packageProject,table.getName(),"Dto",title(),itoFolder));

        StringBuffer bufferFQ = new StringBuffer();
        bufferFQ.append("package ").append(packageProject).append(".").append(itoFolder).append(".").append(tbNameSp(table.getName()).toLowerCase()).append(";").append("\n")
        .append("import vn.unitech.qtht.service.dto.base.FindIto;").append("\n")
                .append("\n")
                .append(" /**").append("\n")
                .append(" * FQPIto "+tbNameSp(table.getName())+"FQPIto.").append("\n")
                .append(" * Author thucnh .").append("\n")
                .append(" * Create at "+title()+" ").append(".").append("\n")
                .append(" */ ").append("\n")
                .append("public class "+tbNameSp(table.getName())+"FQPIto extends FindIto{}");

        for (Column colum: table.getColums()){
            buffer.append("   private ").append(" ")
                    .append(abstractOrmMappingDataConverter.convertToJavaDataType(colum.getDataType()))
                    .append(" ")
                    .append(columNameSp(colum.getFieldName())).append(" ;").append("\n");
            bufferDto.append("   private ").append(" ")
                    .append(abstractOrmMappingDataConverter.convertToJavaDataType(colum.getDataType()))
                    .append(" ")
                    .append(columNameSp(colum.getFieldName())).append(" ;").append("\n");
        }
        buffer.append("}");
        bufferDto.append("}");
        itoFolder += "/".concat(tbNameSp(table.getName()).toLowerCase());
       ExcuteFile.createFile(this.path,itoFolder,tbNameSp(table.getName()).concat("Ito"),buffer.toString(),pushMessageService);
       ExcuteFile.createFile(this.path,itoFolder,tbNameSp(table.getName()).concat("Dto"),bufferDto.toString(),pushMessageService);
       ExcuteFile.createFile(this.path,itoFolder,tbNameSp(table.getName()).concat("FQPIto"),bufferFQ.toString(),pushMessageService);
    }

    protected  void bodySpec(Table table,String packageProject,String entitiFoler,String specFolder) throws IOException {
        String tableName = tbNameSp(table.getName());
        StringBuffer buffer = new StringBuffer();
        buffer.append("package ").append(packageProject).append(".").append(specFolder).append(";").append("\n")
                .append("import java.util.*").append("\n")
                .append("import javax.persistence.criteria.*").append("\n")
                .append("import org.springframework.data.jpa.domain.Specification;").append("\n")
                .append("import org.springframework.util.StringUtils;").append("\n")
                .append("import ").append(packageProject).append(".").append(entitiFoler).append(".").append(tableName).append(";").append("\n")
                .append("\n")
                .append(" /**").append("\n")
                .append(" * Specification "+tbNameSp(table.getName())+"Specification.").append("\n")
                .append(" * Author thucnh .").append("\n")
                .append(" * Create at "+title()+" ").append(".").append("\n")
                .append(" */ ").append("\n")
                .append("public class "+tableName+"Spec implements Specification<"+tableName+">{").append("\n").append("\n");
                for (Column colum : table.getColums() ){
                    buffer.append("    private ").append(abstractOrmMappingDataConverter.convertToJavaDataType(colum.getDataType())).append(columNameSp(colum.getFieldName())).append(";").append("\n");
                }
                 buffer.append("\n");
                buffer.append("  public "+tableName+"Spec(Map<String, Object> filter)  {").append("\n");
                for (Column colum : table.getColums() ){
                    buffer.append("     Object ").append(columNameSp(colum.getFieldName()))
                          .append(" = ").append("null != filter.get(\""+columNameSp(colum.getFieldName())+"\") ? filter.get(\""+columNameSp(colum.getFieldName())+"\")").append("\n");
                }
                 buffer.append("    }").append("\n")
                .append("  @Override").append("\n")
                .append("  public Predicate toPredicate(Root<"+tableName+"> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {").append("\n")
                .append("    List<Predicate> predicates = new LinkedList<>();").append("\n")
                .append("  return cb.and(predicates.toArray(new Predicate[]{}));").append("\n")
                .append("  }").append("\n");

        buffer.append("}");
        ExcuteFile.createFile(this.path,specFolder,tbNameSp(table.getName()).concat("Spec"),buffer.toString(),pushMessageService);
     }





    protected  void bodyRepository(Table table,String packageProject,String repositoryFolder,String entityFolder ) throws IOException {
        StringBuffer buffer = new StringBuffer();
        buffer.append(" package ").append(packageProject).append(".").append(repositoryFolder).append("; \n")
                .append(" import ").append(packageProject).append(entityFolder).append(".").append(tbNameSp(table.getName())).append(";").append("\n")
                .append(" import org.springframework.data.jpa.repository.JpaRepository; \n")
                .append(" import org.springframework.stereotype.Repository; \n")
                .append("\n")
                .append("\n")
                .append(" /**").append("\n")
                .append(" * Repository "+tbNameSp(table.getName())+"Repository.").append("\n")
                .append(" * Author thucnh .").append("\n")
                .append(" * Create at "+title()+" ").append(".").append("\n")
                .append(" */ ").append("\n")
                .append(" @Repository \n")
                .append(" public interface ").append(tbNameSp(table.getName())).append("Repository ")
                .append(" extends ").append("JpaRepository<").append(tbNameSp(table.getName())).append(",") .append("Long")
                .append(">").append(",").append("JpaSpecificationExecutor").append("<").append(tbNameSp(table.getName())).append(">")
                .append("{ ").append("\n");
        buffer.append("}");
        ExcuteFile.createFile(this.path,repositoryFolder,tbNameSp(table.getName()).concat("Repository"),buffer.toString(),pushMessageService);
    }

    protected  void bodyService(Table table,String packageProject,String serviceFolder,String entityFolder,String itoFolder ) throws IOException {
        String tabaleName = tbNameSp(table.getName());
        StringBuffer  buffer = new StringBuffer();
        buffer.append("package ").append(packageProject).append(".").append(serviceFolder).append("; \n")
                .append("import org.springframework.data.domain.Page; \n")
                .append("import org.springframework.data.domain.Pageable; \n")
                .append("import java.util.*; \n")
                .append("import ").append(packageProject).append(".").append(entityFolder).append(".").append(tabaleName).append(";").append("\n")
                .append("import ").append(packageProject).append(".").append(itoFolder).append(".").append(tabaleName).append(".*").append("\n")
                .append("\n")
                .append("\n")
                .append(" /**").append("\n")
                .append(" * Service "+tbNameSp(table.getName())+"Service.").append("\n")
                .append(" * Author thucnh .").append("\n")
                .append(" * Create at "+title()+" ").append(".").append("\n")
                .append(" */ ").append("\n")
                .append("public interface ").append(tabaleName).append("Service ").append("{ ")
                // save param Entity
                .append("\n")
                .append(tabaleName).append(" save(").append(tabaleName)
                .append(" ").append(tabaleName.toLowerCase()).append(")").append(";")
                .append("\n")
                // save param Ito
                .append(tabaleName).append(" save(").append(tabaleName).append("Ito")
                .append(" ").append(tabaleName.toLowerCase()).append(")").append(";")
                .append("\n")
                // findById
                .append(tabaleName).append(" findById(").append("Long")
                .append(" ").append("id").append(")").append(";")
                .append("\n")

                // deleteById
                .append("void").append(" deleteById(").append("Long")
                .append(" ").append("id").append(")").append(";")
                .append("\n")

                // deleteById
                .append("void").append(" deleteByIds(").append("Set<Long>")
                .append(" ").append("ids").append(")").append(";")
                .append("\n")
                // saves
                .append("List<").append(tabaleName).append("> ")
                .append(" saves(")
                .append("List<").append(tabaleName).append("> ")
                .append(tabaleName.toLowerCase()).append("s").append(");")
                .append("\n")
                // Page
                .append("Page<").append(tabaleName).append("> ").append(" pages(").append(tabaleName).append(" ").append(tabaleName.toLowerCase())
                .append(",").append("Pageable pageable") .append(")").append(";")
                .append("\n")
                .append("Page<").append(tabaleName).append("> ").append(" findAll(").append(tabaleName).append("FQPIto ").append(tabaleName.toLowerCase())
                .append(",").append("Pageable pageable") .append(")").append(";")
                .append("\n");
        buffer.append("}") ;
        ExcuteFile.createFile(this.path,serviceFolder,tbNameSp(table.getName()).concat("Service"),buffer.toString(),pushMessageService);

    }

    protected  void bodyServiceImpl(Table table,String packageProject,String serviceFolder,String repoFolder, String entityFolder,String itoFolder,String specFolder ) throws IOException {
        String tabaleName = tbNameSp(table.getName());
        String repositoryname = tabaleName.toLowerCase().concat("Repository");
        StringBuffer  buffer = new StringBuffer();

        buffer.append("package ").append(packageProject).append(".").append(serviceFolder).append("; \n")
                .append("import org.springframework.data.domain.Page; \n")
                .append("import org.springframework.data.domain.Pageable; \n")
                .append("import org.springframework.stereotype.Service; \n")
                .append("import org.springframework.beans.factory.annotation.Autowired; \n")
                .append("import org.springframework.data.domain.*; \n")
                .append("import vn.unitech.qtht.utils.DTOmapper; \n")
                .append("import ").append(packageProject).append(".").append(repoFolder).append(".").append(tabaleName).append("Repository;").append("\n")
                .append("import ").append(packageProject).append(".").append(specFolder).append(".").append(tabaleName).append("Spec;").append("\n")
                .append("import ").append(packageProject).append(".").append(entityFolder).append(".").append(tabaleName).append(";").append("\n")
                .append("import ").append(packageProject).append(".").append(itoFolder).append(".").append(tabaleName).append(".*;").append("\n")
                .append("import java.util.*; \n")
                .append("\n")
                .append(" /**").append("\n")
                .append(" * ServiceImpl "+tbNameSp(table.getName())+"ServiceImpl.").append("\n")
                .append(" * Author thucnh .").append("\n")
                .append(" * Create at "+title()+" ").append(".").append("\n")
                .append(" */ ").append("\n")
                .append("\n")
                .append("@Service")
                .append("\n")
                .append("public class ").append(" ").append(tabaleName).append("ServiceImpl").append(" implements ")
                .append(tabaleName).append("Service ").append("{ ")
                .append("\n").append("\n")
                .append(" @Autowired")
                .append("\n")
                .append(" private ").append(tabaleName).append("Repository ")
                .append(tabaleName.toLowerCase()).append("Repository;")
                .append("\n")
                // save parram Entity
                .append("\n")
                .append(" @Override").append("\n")
                .append(" public ").append(tabaleName).append(" save(").append(tabaleName)
                .append(" ").append(tabaleName.toLowerCase()).append(")").append("{").append("\n")
                .append(" return ").append(tabaleName.toLowerCase()).append("Repository.").append("save(")
                .append(tabaleName.toLowerCase()).append(");")
                .append("\n")
                .append(" }")
                .append("\n")

                // save parram Ito
                .append("\n")
                .append(" @Override").append("\n")
                .append(" public ").append(tabaleName).append(" save(").append(tabaleName).append("Ito")
                .append(" ").append(tabaleName.toLowerCase()).append(")").append("{").append("\n")
                .append(" "+tabaleName+" data = DTOmapper.map("+tabaleName.toLowerCase()+","+tabaleName+".class);").append("\n")
                .append(" return ").append(tabaleName.toLowerCase()).append("Repository.").append("save(")
                .append(" data").append(");")
                .append("\n")
                .append(" }")
                .append("\n")
                // findById
                .append(" @Override").append("\n")
                .append(" public ") .append(tabaleName).append(" findById(").append("Long")
                .append(" ").append("id").append(")").append("{").append("\n") .append(" return ").append(tabaleName.toLowerCase()).append("Repository.").append("findById(")
                .append("id").append(").orElse(null);")
                .append("\n")
                .append("}")
                .append("\n")
                .append("\n")

                // deleteById
                .append(" @Override").append("\n")
                .append(" public ") .append("void").append(" deleteById(").append("Long")
                .append(" ").append("id").append(")").append("{").append("\n")
                .append("  ").append(tabaleName.toLowerCase()).append("Repository.").append("delete(")
                .append("id").append(")")
                .append("\n")
                .append("}")
                .append("\n")
                .append("\n")

                // deleteByIds
                .append(" @Override").append("\n")
                .append(" public ") .append("void").append(" deleteByIds(").append("Set<Long>")
                .append(" ").append("ids").append(")").append("{").append("\n")
                .append("  ").append(tabaleName.toLowerCase()).append("Repository.").append("deletes(")
                .append("ids").append(")")
                .append("\n")
                .append("}")
                .append("\n")
                .append("\n")
                // saves
                .append(" @Override").append("\n")
                .append(" public ").append("List<").append(tabaleName).append("> ")
                .append(" saves(")
                .append(" List<").append(tabaleName).append("> ")
                .append(tabaleName.toLowerCase()).append("s").append("){")
                .append("\n")
                .append(" return ").append(tabaleName.toLowerCase()).append("Repository.").append("save(")
                .append(tabaleName.toLowerCase()).append("s );")
                .append("\n")
                .append("}")
                .append("\n")
                .append("\n")

                // Page
                .append(" @Override").append("\n")
                .append(" public ").append("Page<").append(tabaleName).append("> ").append("pages(")
                .append(tabaleName).append(" ")
                .append(tabaleName.toLowerCase())
                .append(",")
                .append(" Pageable pageable")
                .append(")")
                .append(" {")
                .append("\n")
                .append(" return null; ").append("\n")
                .append("}")
//                .append(tabaleName.toLowerCase()).append("Repository.").append("findAll(")
//                .append(tabaleName.toLowerCase()).append(" , pageable );")
                .append("\n")
                .append("\n")

                // Page
                .append(" @Override").append("\n")
                .append(" public ").append("Page<").append(tabaleName).append("> ") .append("findAll(")
                .append(tabaleName).append("FQPIto ") .append("input")
                .append(",")
                .append("Pageable pageable")
                .append(")")
                .append("{")
                .append("\n")

                .append(" Sort sortDef = null; ").append("\n")
                .append(" if (!org.springframework.util.StringUtils.isEmpty(input.getQueryParams().getSortField())){ ").append("\n")
                .append("    sortDef = org.springframework.data.domain.Sort.by(  new Sort.Order(").append("\n")
                .append("    org.springframework.data.domain.Sort.Direction.fromString(input.getQueryParams().getSortOrder().toUpperCase()),").append("\n")
                .append("    input.getQueryParams().getSortField()").append("));").append("\n")
                .append("   }else {").append("\n")
                .append("    sortDef = org.springframework.data.domain.Sort.by(org.springframework.data.domain.Sort.Order.asc(\"id\"));").append("\n")
                .append("  }").append("\n")
                .append("   Pageable pageable = PageRequest.of(input.getQueryParams().getPageNumber()-1, input.getQueryParams().getPageSize(), sortDef);").append("\n")
                .append("   Page<"+tabaleName+"> data = "+repositoryname+".findAll(new "+tabaleName+"Spec(input.getQueryParams().getFilter()), pageable);").append("\n")
                .append("   return data ; ")
                .append("\n")
                .append("   }")
                .append("\n");

        buffer.append("}") ;
        ExcuteFile.createFile(this.path,serviceFolder,tbNameSp(table.getName()).concat("ServiceImpl"),buffer.toString(),pushMessageService);
    }

    protected  void bodyController(Table table,String packageProject,String controllerFolder,String serviceFolder, String entityFolder,String itoFolder ) throws IOException {
        String tablename = tbNameSp(table.getName());
        String serviceName = tablename.toLowerCase().concat("Service ");
        String serviceNameVar = tablename.toLowerCase().concat("Service");
        StringBuffer  buffer = new StringBuffer();
        buffer.append("package ").append(packageProject).append(".").append(controllerFolder).append("; \n")
                .append("import org.springframework.stereotype.Controller; \n")
                .append("import org.springframework.web.bind.annotation.*; \n")
                .append("import javax.servlet.http.HttpServletRequest; \n")
                .append("import org.springframework.http.*; \n")
                .append("import java.util.*; \n")
                .append("import org.springframework.data.domain.*; \n")
                .append("import org.springframework.stereotype.Service; \n")
                .append("import org.springframework.beans.factory.annotation.Autowired; \n")
                .append("import ").append(packageProject)
                .append(".").append(serviceFolder).append(".").append(tablename).append("Service;").append("\n") // service
                .append("import ").append(packageProject).append(".").append(entityFolder).append(".").append(tablename).append(";").append("\n") // entity
                .append("import ").append(packageProject).append(".").append(itoFolder).append(".").append(tablename).append(".*;").append("\n") // Ito
                .append("import java.util.*; \n")
                .append("\n")
                .append("\n")
                .append(" /**").append("\n")
                .append(" * Controller "+tbNameSp(table.getName())+"Resource.").append("\n")
                .append(" * Author thucnh .").append("\n")
                .append(" * Create at "+title()+" ").append(".").append("\n")
                .append(" */ ").append("\n")
                .append("\n")
                .append("@RestController").append("\n")
                .append("@RequestMapping(\"/api\")").append("\n")
                .append("@Slf4j").append("\n")
                .append("public class ").append(" ").append(tablename).append("Resource ").append("{ ")
                .append("\n")
                .append("\n")
                .append("@Value(\"${jhipster.clientApp.name}\")").append("\n")
                .append("private String applicationName;").append("\n")
                .append("@Autowired")
                .append("\n")
                .append("private ").append(serviceName).append(" ")
                .append(tablename.toLowerCase()).append("Service;")
                .append("\n").append("\n");
                 StringBuffer  bufferGetById = new StringBuffer();
                 bufferGetById.append(" @GetMapping(value = \"/"+tablename.toLowerCase()+"/{id}\")").append("\n")
                             .append( " public ResponseEntity<Map<String, Object>> getById(@PathVariable(\"id\") long id) { ").append("\n")
                             .append( "     Map<String, Object> result = new HashMap<>();").append("\n")
                             .append( "     "+tablename+" data = "+serviceNameVar+".findById(id);").append("\n")
                             .append( "     result.put(\"item\",data);").append("\n")
                             .append( "   return CommonConstant.Service.setResult( applicationName, \""+tablename+"\", result);").append("\n")
                             .append( " }").append("\n").append("\n");
                 buffer.append(bufferGetById);
                 StringBuffer  bufferSave = new StringBuffer();
                 bufferSave
                        .append(" @PostMapping(value = \"/"+tablename.toLowerCase()+"\")"    ).append("\n")
                        .append(" public ResponseEntity<Map<String, Object>> save(@Valid @RequestBody "+tablename+"Ito  "+tablename.toLowerCase()+"Ito) { " ).append("\n")
                        .append("      Map<String, Object> result = new HashMap<>();"  ).append("\n")
                        .append("      try { ").append("\n")
                        .append("           "+tablename+" "+tablename.toLowerCase()+" =  "+serviceNameVar+".save("+tablename.toLowerCase()+"Ito);").append("\n")
                        .append("           result.put(\"item\", "+tablename.toLowerCase()+");").append("\n")
                        .append("     } catch (Exception e) {").append("\n")
                        .append("       result.put(\"msg\",e.getMessage());").append("\n")
                        .append("     } ").append("\n")
                        .append( " return CommonConstant.Service.setResult( applicationName, \""+tablename+"\", result);").append("\n") .append( " }").append("\n").append("\n");
                 buffer.append(bufferSave);
                 StringBuffer  bufferUpdate = new StringBuffer();
                 bufferUpdate
                        .append(" @PutMapping(value = \"/"+tablename.toLowerCase()+"/{id}\")"    ).append("\n")
                        .append(" public ResponseEntity<Map<String, Object>> save( @PathVariable Long id, @Valid @RequestBody "+tablename+"Ito  "+tablename.toLowerCase()+"Ito) { " ).append("\n")
                        .append("      Map<String, Object> result = new HashMap<>();"  ).append("\n")
                        .append("      try { ").append("\n")
                        .append("      "+tablename.toLowerCase()+"Ito.setId(id); ").append("\n")
                        .append("      "+tablename+" "+tablename.toLowerCase()+" =  "+serviceNameVar+".save("+tablename.toLowerCase()+"Ito);").append("\n")
                        .append("           result.put(\"item\", "+tablename.toLowerCase()+");").append("\n")
                        .append("     } catch (Exception e) {").append("\n")
                        .append("        result.put(\"msg\",e.getMessage());").append("\n")
                        .append("     } ").append("\n")
                        .append( "  return CommonConstant.Service.setResult( applicationName,\""+tablename+"\", result);").append("\n") .append( " }").append("\n").append("\n");
                 buffer.append(bufferUpdate);
                 StringBuffer  bufferFind = new StringBuffer();
                     bufferFind
                     .append("  @PostMapping(value = \"/"+tablename.toLowerCase()+"/find\")").append("\n")
                     .append("  public ResponseEntity<Map<String, Object>> find"+tablename+"(@RequestBody "+tablename+"FQPIto "+tablename.toLowerCase()+") throws URISyntaxException { ").append("\n")
                     .append("      log.debug(\"REST request to find : {}\");").append("\n")
                     .append("      Map<String, Object> result = new HashMap<>();").append("\n")
                     .append("      Page<"+tablename+">  page = "+serviceNameVar+".findAll("+tablename.toLowerCase()+");").append("\n")
                     .append("      result.put(\"entities\", page.getContent());").append("\n")
                     .append("      result.put(\"totalCount\", page.getTotalElements());").append("\n")
                     .append("      HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);").append("\n")
                     .append( "  return CommonConstant.Service.setResult( applicationName, \""+tablename+"\", result);").append("\n") .append( " }").append("\n").append("\n");
                     buffer.append(bufferFind);

                  StringBuffer  bufferDelete = new StringBuffer();
                   bufferDelete
                        .append("@DeleteMapping(value = \"/"+tablename.toLowerCase()+ "/{id}\")"    ).append("\n")
                        .append("public ResponseEntity<Void> delete+"+tablename+"(@PathVariable Long id) { ").append("\n")
                        .append("       log.debug(\"REST request to delete  : {}\", id); ").append("\n")
                        .append("       "+serviceNameVar+".deleteById(id);").append("\n")
                        .append("  return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, \""+tablename+"\", id.toString())).build(); ").append("\n")
                        .append("}").append("\n");
                buffer.append(bufferDelete);
                StringBuffer  bufferDeleteS = new StringBuffer();
                bufferDeleteS
                .append("@DeleteMapping(value = \"/"+tablename.toLowerCase()+ "/deletes\")"    ).append("\n")
                .append("public ResponseEntity<Void> delete+"+tablename+"(@RequestBody Set<Long> ids) { ").append("\n")
                .append("       log.debug(\"REST request to delete  : {}\", ids); ").append("\n")
                .append("       "+serviceNameVar+".deleteByIds(ids);").append("\n")
                .append("  return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, \""+tablename+"\", ids.toString())).build(); ").append("\n")
                .append("}").append("\n");
                 buffer.append(bufferDeleteS);

        buffer.append("}").append("\n");
        ExcuteFile.createFile(this.path,controllerFolder,tbNameSp(table.getName()).concat("Resource"),buffer.toString(),pushMessageService);
    }

    protected static String title(){
        Date now = new Date();
        String date = new SimpleDateFormat("MM-dd-yyyy").format(now);
        StringBuffer title = new StringBuffer();
        title.append(date).append(" time ")
                .append(now.getHours())
                .append("h")
                .append(now.getMinutes());
        return  title.toString();
    }

    public static  String tbNameSp(String tb){
        String result = "" ;
        if (tb.indexOf("-") != -1) {
            tb =  tb.replaceAll("-", "  ");
            tb =  WordUtils.capitalize(tb);
            tb =  tb.replaceAll("\\s+","");
            result= tb;

        }else if (tb.indexOf("_") != 1){
            tb =  tb.replaceAll("_", " ");
            tb =  WordUtils.capitalize(tb);
            tb =  tb.replaceAll("\\s+","");
            result= tb;
        }else{
            tb =   StringUtils.capitalize(tb);
            result= tb;
        }
        return result ;
    }

    public static  String columNameSp(String tb){
        String result = "" ;
        if (tb.indexOf("-") != -1) {
            tb =  tb.replaceAll("-", "  ");
            tb =  WordUtils.capitalize(tb);
            tb =  tb.replaceAll("\\s+","");
            tb = WordUtils.uncapitalize(tb) ;
            result= tb;

        }else if (tb.indexOf("_") != 1){
            tb =  tb.replaceAll("_", " ");
            tb =  WordUtils.capitalize(tb);
            tb =  tb.replaceAll("\\s+","");
            result= WordUtils.uncapitalize(tb) ;
        }else{
            result= tb;
        }
        return result ;
    }

}
