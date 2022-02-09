package com.thucnh96.jpa.service.orm;

import com.thucnh96.jpa.constants.JpaAnotation;
import com.thucnh96.jpa.converter.AbstractOrmMappingDataConverter;
import com.thucnh96.jpa.modal.Column;
import com.thucnh96.jpa.modal.Table;
import com.thucnh96.jpa.progress.ExcuteFile;
import com.thucnh96.jpa.service.PushMessageService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
                .append(JpaAnotation.javaxPersistence)
                .append(JpaAnotation.importJavaUtil)
                .append(JpaAnotation.importLombok)
                .append("\n")
                .append("\n")
                .append(" /**").append("\n")
                .append(" * Entity "+tbNameSp(tableName)+".").append("\n")
                .append(" * Author thucnh .").append("\n")
                .append(" * Create at "+title()+" ").append(".").append("\n")
                .append(" */ ").append("\n")
                .append(JpaAnotation.Data)
                .append(JpaAnotation.NoArgsConstructor)
                .append(JpaAnotation.AllArgsConstructor)
                .append(JpaAnotation.Entity)
                .append("@Table(name = \"" +tableName+ "\") \n")
                .append("public class "+tbNameSp(tableName)+" ").append("extends AbstractCustomAuditingEntity").append("{ ")
                .append("\n").append("\n");
        return buffer.toString();
    }
    protected static String headerAllDto(String packageProject, String tableName,String type, String title,String folder){
        StringBuffer buffer = new StringBuffer();
        buffer.append(" package ").append(packageProject).append(".").append(folder).append(".").append(tbNameSp(tableName).toLowerCase()).append("; \n")
                .append(JpaAnotation.importJavaUtil)
                .append(JpaAnotation.importLombok)
                .append("\n")
                .append("\n")
                .append(" /**")
                .append(" * "+type+" "+tbNameSp(tableName)+"").append(type).append("\n")
                .append(" * Author thucnh .").append("\n")
                .append(" * Create at "+title+" ").append("\n")
                .append(" */ ").append("\n")
                .append(JpaAnotation.Data)
                .append(JpaAnotation.NoArgsConstructor)
                .append(JpaAnotation.AllArgsConstructor)
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
                buffer.append("   ").append("@GeneratedValue(strategy = GenerationType.IDENTITY)").append("\n");
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
        ExcuteFile.createFile(this.path,entityFolder,tbNameSp(table.getName()).concat(""),buffer.toString(),pushMessageService);

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
                .append(JpaAnotation.importJavaUtil)
                .append(JpaAnotation.javaxPersistenceCriteria)
                .append(JpaAnotation.importDataDomain)
                .append(JpaAnotation.importSpringframeworkUtil)
                .append("import ").append(packageProject).append(".").append(entitiFoler).append(".").append(tableName).append(";").append("\n")
                .append("import ").append(packageProject).append(".").append(entitiFoler).append(".").append(tableName).append("_;").append("\n")
                .append("\n")
                .append(" /**").append("\n")
                .append(" * Specification "+tbNameSp(table.getName())+"Specification.").append("\n")
                .append(" * Author thucnh .").append("\n")
                .append(" * Create at "+title()+" ").append(".").append("\n")
                .append(" */ ").append("\n")
                .append("public class "+tableName+"Specification extends AbstractSpecification<"+tableName+">{").append("\n").append("\n");
                for (Column colum : table.getColums() ){
                    buffer.append("    private ").append(abstractOrmMappingDataConverter.convertToJavaDataType(colum.getDataType())).append(" ").append(columNameSp(colum.getFieldName())).append(";").append("\n");
                }
                buffer.append("private String search;");
                buffer.append("\n");
                buffer.append("  public "+tableName+"Specification(Map<String, Object> filter)  {").append("\n")
                        .append("    super(filter);").append("\n");
                for (Column colum : table.getColums() ){
                    buffer.append("    ").append("this").append(".").append(columNameSp(colum.getFieldName()))
                          .append(" = ").append("null != filter.get("+tableName+"").append("_.").append(columNameSp(colum.getFieldName()).toUpperCase()).append(")").append(" ? filter.get(").append(tableName).append("_.").append(columNameSp(colum.getFieldName()).toUpperCase()).append(")").append(" : null ;").append("\n");
                }
                 buffer.append("this.search = null != filter.get("+tableName+"_.SEARCH) ? (String) filter.get("+tableName+"_.SEARCH) : null;").append("\n");
                 buffer.append("    }").append("\n").append("\n")
                .append("  @Override").append("\n")
                .append("  public Predicate toPredicate(Root<"+tableName+"> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {").append("\n")
                 .append("  List<Predicate> predicates = new LinkedList<>();").append("\n")
                 .append("  List<Predicate> predicatesOr = new LinkedList<>();").append("\n")
                 .append("  predicates.add(cb.equal(root.get("+tableName+"_.isDeleted), 0)")
                 .append("if (!StringUtils.isEmpty(this.search)) {\n" +
                            "            predicates.add(cb.like(root.get("+tableName+"_.search), \"%\" + this.search.toLowerCase() + \"%\"));\n" +
                            "        }else {").append("\n");
                    for (Column colum : table.getColums() ){
                        buffer.append("if (!StringUtils.isEmpty("+columNameSp(colum.getFieldName())+")) {").append("\n")
                                .append("predicatesOr.add(cb.like(cb.lower(root.get("+tableName+"_."+columNameSp(colum.getFieldName())+")), \"%\" + "+columNameSp(colum.getFieldName())+".trim().toLowerCase() + \"%\"));").append("\n")
                                .append("}").append("\n");
                   }
                buffer.append("}").append("\n");

                 buffer.append("if (predicatesOr.size() > 0) {\n" +
                                 "            Predicate predicateAllOr = cb.or(predicatesOr.toArray(new Predicate[]{}));\n" +
                                 "            predicates.add(predicateAllOr);\n" +
                                 "        }")
                .append("  return cb.and(predicates.toArray(new Predicate[]{}));").append("\n")
                .append("  }").append("\n");

        buffer.append("}");
        ExcuteFile.createFile(this.path,specFolder,tbNameSp(table.getName()).concat("Specification"),buffer.toString(),pushMessageService);
     }





    protected  void bodyRepository(Table table,String packageProject,String repositoryFolder,String entityFolder ) throws IOException {
        StringBuffer buffer = new StringBuffer();
        buffer.append(" package ").append(packageProject).append(".").append(repositoryFolder).append("; \n")
                .append(" import ").append(packageProject).append(".").append(entityFolder).append(".").append(tbNameSp(table.getName())).append(";").append("\n")
                .append(JpaAnotation.importJpaRepository)
                .append(JpaAnotation.importRepository)
                .append("\n")
                .append("\n")
                .append(" /**").append("\n")
                .append(" * Repository "+tbNameSp(table.getName())+"Dao.").append("\n")
                .append(" * Author thucnh .").append("\n")
                .append(" * Create at "+title()+" ").append(".").append("\n")
                .append(" */ ").append("\n")
                .append(" @Repository \n")
                .append(" public interface ").append(tbNameSp(table.getName())).append("Dao ")
                .append(" extends ").append("BaseDao<").append(tbNameSp(table.getName())).append(",") .append("Long")
                .append(">").append("{ ").append("\n");
        buffer.append("}");
        ExcuteFile.createFile(this.path,repositoryFolder,tbNameSp(table.getName()).concat("Dao"),buffer.toString(),pushMessageService);
    }

    protected  void bodyService(Table table,String packageProject,String serviceFolder,String entityFolder,String itoFolder ) throws IOException {
        String tabaleName = tbNameSp(table.getName());
        StringBuffer  buffer = new StringBuffer();
        buffer.append("package ").append(packageProject).append(".").append(serviceFolder).append("; \n")
                .append(JpaAnotation.importDataDomain)
                .append(JpaAnotation.importJavaUtil)
                .append("import ").append(packageProject).append(".").append(entityFolder).append(".").append(tabaleName).append(";").append("\n")
                .append("import ").append(packageProject).append(".").append(itoFolder).append(".").append(tabaleName).append(".*").append("\n")
                .append("import ").append(packageProject).append(".").append(itoFolder).append(".").append("base.SearchIto").append(";").append("\n")
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
                .append(tabaleName).append(" getByid(").append("Long")
                .append(" ").append("id").append(")").append(";")
                .append("\n")

                // deleteById
                .append("void").append(" delete(").append("Long")
                .append(" ").append("id").append(")").append(";")
                .append("\n")

                // deleteById
                .append("void").append(" deleteByids(").append("List<Long>")
                .append(" ").append("ids").append(")").append(";")
                .append("\n")

                .append("List<tabaleName> findAll();")
                .append("\n")
                // Page
                .append("Page<").append(tabaleName).append("> ").append(" findAll(").append("FindIto search ").append(")").append(";")
                .append("\n")
                .append("Page<").append(tabaleName).append("> ").append(" findAll(").append("SearchIto search ").append(")").append(";")
                .append("\n");

        buffer.append("}") ;
        ExcuteFile.createFile(this.path,serviceFolder,tbNameSp(table.getName()).concat("Service"),buffer.toString(),pushMessageService);

    }

    protected  void bodyServiceImpl(Table table,String packageProject,String serviceFolder,String repoFolder, String entityFolder,String itoFolder,String specFolder ) throws IOException {
        String tabaleName = tbNameSp(table.getName());
        String repositoryname = tabaleName.toLowerCase().concat("Repository");
        StringBuffer  buffer = new StringBuffer();

        buffer.append(JpaAnotation.packageStr).append(packageProject).append(".").append(serviceFolder).append("; \n")
                .append(JpaAnotation.importDataDomain)
                .append(JpaAnotation.importService)
                .append(JpaAnotation.importAutowired)
                .append(JpaAnotation.importStr).append(packageProject).append(".").append("utils").append(".").append("DTOmapper;").append("\n")
                .append(JpaAnotation.importStr).append(packageProject).append(".").append(repoFolder).append(".").append(tabaleName).append("Repository;").append("\n")
                .append(JpaAnotation.importStr).append(packageProject).append(".").append(specFolder).append(".").append(tabaleName).append("Spec;").append("\n")
                .append(JpaAnotation.importStr).append(packageProject).append(".").append(entityFolder).append(".").append(tabaleName).append(";").append("\n")
                .append(JpaAnotation.importStr).append(packageProject).append(".").append(itoFolder).append(".").append(tabaleName).append(".*;").append("\n")
                .append(JpaAnotation.importJavaUtil)
                .append("\n")
                .append(" /**").append("\n")
                .append(" * ServiceImpl "+tbNameSp(table.getName())+"ServiceImpl.").append("\n")
                .append(" * Author thucnh .").append("\n")
                .append(" * Create at "+title()+" ").append(".").append("\n")
                .append(" */ ").append("\n")
                .append("\n")
                .append(JpaAnotation.Service)
                .append("public class ").append(" ").append(tabaleName).append("ServiceImpl extends BaseService<"+tbNameSp(table.getName())+", "+tbNameSp(table.getName())+"Dao>").append(" implements ")
                .append(tabaleName).append("Service ").append("{ ")
                .append("\n").append("\n")
                // save parram Entity
                .append("\n")
                .append(" @Override").append("\n")
                .append(" public ").append(tabaleName).append(" save(").append(tabaleName)
                .append(" ").append(tabaleName.toLowerCase()).append(")").append("{").append("\n")
                .append(" return ").append("saveEntity").append("(")
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
                .append(" return ").append("saveEntity").append("(")
                .append(" data").append(");")
                .append("\n")
                .append(" }")
                .append("\n")
                // findById
                .append(" @Override").append("\n")
                .append(" public ") .append(tabaleName).append(" getByid(").append("Long")
                .append(" ").append("id").append(")").append("{").append("\n") .append(" return ").append("findEntityById(")
                .append("id").append(")").append(";")
                .append("\n")
                .append("}")
                .append("\n")
                .append("\n")

                // deleteById
                .append(" @Override").append("\n")
                .append(" public ") .append("void").append(" delete(").append("Long")
                .append(" ").append("id").append(")").append("{").append("\n")
                .append("  ").append("deleteEntityById(")
                .append("id").append(")").append(";")
                .append("\n")
                .append("}")
                .append("\n")
                .append("\n")

                // deleteByIds
                .append(" @Override").append("\n")
                .append(" public ") .append("void").append(" deleteByids(").append("List<Long>")
                .append(" ").append("ids").append(")").append("{").append("\n")
                .append("  ").append("deleteEntityByIds(")
                .append("ids").append(")").append(";")
                .append("\n")
                .append("}")
                .append("\n")
                .append("\n")

                // Page
                .append(" @Override").append("\n")
                .append(" public ").append("Page<").append(tabaleName).append("> ").append("findAll(")
                .append("FindIto search")
                .append(")")
                .append(" {")
                .append("\n")
                .append(" return findAllPage(search,new "+tabaleName+"Specification(search.getQueryParams().getFilter())); ").append("\n")
                .append("}")
//                .append(tabaleName.toLowerCase()).append("Repository.").append("findAll(")
//                .append(tabaleName.toLowerCase()).append(" , pageable );")
                .append("\n")
                .append("\n")

                // List
                .append(" @Override").append("\n")
                .append(" public ").append("List<").append(tabaleName).append("> ").append("findAll()")
                .append(" {")
                .append("\n")
                .append(" return this.dao.findAllNotDelete(); ").append("\n")
                .append("}")
//                .append(tabaleName.toLowerCase()).append("Repository.").append("findAll(")
//                .append(tabaleName.toLowerCase()).append(" , pageable );")
                .append("\n")
                .append("\n")

        // Page
                .append(" @Override").append("\n")
                .append(" public ").append("Page<").append(tabaleName).append("> ").append("findAll(")
                .append("SearchIto search")
                .append(")")
                .append(" {")
                .append("\n")
                .append(" return findAllPageSearch(search,new "+tabaleName+"Specification(search.getFilter())); ").append("\n")
                .append("}")
//                .append(tabaleName.toLowerCase()).append("Repository.").append("findAll(")
//                .append(tabaleName.toLowerCase()).append(" , pageable );")
                .append("\n")
                .append("\n");

        buffer.append("}") ;
        ExcuteFile.createFile(this.path,serviceFolder,tbNameSp(table.getName()).concat("ServiceImpl"),buffer.toString(),pushMessageService);
    }

    protected  void bodyController(Table table,String packageProject,String controllerFolder,String serviceFolder, String entityFolder,String itoFolder ) throws IOException {
        String tablename = tbNameSp(table.getName());
        String serviceName = tablename.concat("Service ");
        String serviceNameVar = serviceName.replaceFirst(serviceName.substring(0,1), serviceName.substring(0,1).toLowerCase());
        StringBuffer  buffer = new StringBuffer();
        buffer.append(JpaAnotation.packageStr).append(packageProject).append(".").append(controllerFolder).append("; \n")
                .append(JpaAnotation.importController)
                .append(JpaAnotation.importWebBindAnnotation)
                .append(JpaAnotation.importHttpServletRequest)
                .append(JpaAnotation.importServletHttp)
                .append(JpaAnotation.importJavaUtil)
                .append(JpaAnotation.importDataDomain)
                .append(JpaAnotation.importService)
                .append(JpaAnotation.importAutowired)
                .append(JpaAnotation.importStr).append(packageProject)
                .append(".").append(serviceFolder).append(".").append(tablename).append("Service;").append("\n") // service
                .append(JpaAnotation.importStr).append(packageProject).append(".").append(entityFolder).append(".").append(tablename).append(";").append("\n") // entity
                .append(JpaAnotation.importStr).append(packageProject).append(".").append(itoFolder).append(".").append(tablename).append(".*;").append("\n") // Ito
                .append(JpaAnotation.importStr).append(packageProject).append(".").append(controllerFolder).append(".").append("base.AbstractAPI;").append("\n") // Ito
                .append(JpaAnotation.importJavaUtil)
                .append("import static com.unitech.base.APIPrefix._API_PREFIX;").append("\n")
                .append("import javax.servlet.http.HttpServletRequest;").append("\n")
                .append("import javax.validation.Valid;").append("\n")
                .append(JpaAnotation.nextLine)
                .append(JpaAnotation.nextLine)
                .append(" /**").append(JpaAnotation.nextLine)
                .append(" * Controller "+tbNameSp(table.getName())+"Resource.").append("\n")
                .append(" * Author thucnh .").append("\n")
                .append(" * Create at "+title()+" ").append(".").append("\n")
                .append(" */ ").append("\n")
                .append("\n")
                .append(JpaAnotation.RestController)
                .append("@RequestMapping(value = _API_PREFIX)").append("\n")
                .append("@Slf4j").append("\n")
                .append("public class ").append(" ").append(tablename).append("Resource extends AbstractAPI").append("{ ")
                .append("\n")
                .append("\n")
                .append(JpaAnotation.Autowired)
                .append("private ").append(serviceName).append(" ")
                .append(serviceNameVar).append(";")
                .append("\n");

                StringBuffer  bufferSearch = new StringBuffer();
                 bufferSearch.append(" @GetMapping(value = \"/"+tablename.toLowerCase()+"\")").append("\n")
                        .append( " public ResponseEntity<Object> search(HttpServletRequest request) { ").append("\n")
                        .append( "     Page<"+tablename+"> data = "+serviceNameVar+".findAll(paramSearch(request));").append("\n")
                        .append( "     return   toResponsePage(data);").append("\n")
                        .append( " }").append("\n").append("\n");
                buffer.append(bufferSearch);


                 StringBuffer  bufferGetById = new StringBuffer();
                 bufferGetById.append(" @GetMapping(value = \"/"+tablename.toLowerCase()+"/{id}\")").append("\n")
                             .append( " public ResponseEntity<Object> getById(@PathVariable(\"id\") long id) { ").append("\n")
                             .append( "     "+tablename+" data = "+serviceNameVar+".getByid(id);").append("\n")
                             .append( "     return   toResponse(data);").append("\n")
                             .append( " }").append("\n").append("\n");
                 buffer.append(bufferGetById);


                 StringBuffer  bufferSave = new StringBuffer();
                 bufferSave
                        .append(" @PostMapping(value = \"/"+tablename.toLowerCase()+"\")"    ).append("\n")
                        .append(" public ResponseEntity<Object> save(@Valid @RequestBody "+tablename+"Ito  "+tablename.toLowerCase()+"Ito) { " ).append("\n")
                        .append("           "+tablename+" "+tablename.toLowerCase()+" =  "+serviceNameVar+".save("+tablename.toLowerCase()+"Ito);").append("\n")
                        .append( "     return   toResponse("+tablename.toLowerCase()+");").append("\n").append("}").append("\n");
                 buffer.append(bufferSave);
                 StringBuffer  bufferUpdate = new StringBuffer();
                 bufferUpdate
                        .append(" @PutMapping(value = \"/"+tablename.toLowerCase()+"/{id}\")"    ).append("\n")
                        .append(" public ResponseEntity<Object> save( @PathVariable Long id, @Valid @RequestBody "+tablename+"Ito  "+tablename.toLowerCase()+"Ito) { " ).append("\n")
                        .append("      "+tablename.toLowerCase()+"Ito.setId(id); ").append("\n")
                        .append("      "+tablename+" "+tablename.toLowerCase()+" =  "+serviceNameVar+".save("+tablename.toLowerCase()+"Ito);").append("\n")
                        .append( "    return   toResponse("+tablename.toLowerCase()+");") .append( " }").append("\n").append("\n");
                 buffer.append(bufferUpdate);
                 StringBuffer  bufferFind = new StringBuffer();
                     bufferFind
                     .append("  @PostMapping(value = \"/"+tablename.toLowerCase()+"/find\")").append("\n")
                     .append("  public ResponseEntity<Object> find"+tablename+"(@RequestBody "+tablename+"FQPIto "+tablename.toLowerCase()+") throws URISyntaxException { ").append("\n")
                     .append("      log.debug(\"REST request to find : {}\");").append("\n")
                     .append("      Map<String, Object> result = new HashMap<>();").append("\n")
                     .append("      Page<"+tablename+">  page = "+serviceNameVar+".findAll("+tablename.toLowerCase()+");").append("\n")
                     .append("      result.put(\"entities\", page.getContent());").append("\n")
                     .append("      result.put(\"totalCount\", page.getTotalElements());").append("\n")
                     .append( "     return toResponse(result); }").append("\n").append("\n");
                     buffer.append(bufferFind);

                  StringBuffer  bufferDelete = new StringBuffer();
                   bufferDelete
                        .append(" @DeleteMapping(value = \"/"+tablename.toLowerCase()+ "/{id}\")"    ).append("\n")
                        .append(" public ResponseEntity<Void> delete"+tablename+"(@PathVariable Long id) { ").append("\n")
                        .append("       log.debug(\"REST request to delete  : {}\", id); ").append("\n")
                        .append("       "+serviceNameVar+".delete(id);").append("\n")
                        .append("  return new ResponseEntity<>(HttpStatus.NO_CONTENT); ").append("\n")
                        .append(" }").append("\n");
                buffer.append(bufferDelete);
                StringBuffer  bufferDeleteS = new StringBuffer();
                bufferDeleteS
                .append(" @DeleteMapping(value = \"/"+tablename.toLowerCase()+ "/deletes\")"    ).append("\n")
                .append(" public ResponseEntity<Void> delete"+tablename+"(@RequestBody List<Long> ids) { ").append("\n")
                .append("       log.debug(\"REST request to delete  : {}\", ids); ").append("\n")
                .append("       "+serviceNameVar+".deleteByids(ids);").append("\n")
                .append(" return new ResponseEntity<>(HttpStatus.NO_CONTENT);").append("\n")
                .append(" }").append("\n");
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

    public abstract void genProgress() throws Exception;
}
