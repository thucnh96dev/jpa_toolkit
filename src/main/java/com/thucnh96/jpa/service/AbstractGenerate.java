package com.thucnh96.jpa.service;

import com.thucnh96.jpa.constants.AppConstants;
import com.thucnh96.jpa.constants.JpaAnotation;
import com.thucnh96.jpa.converter.AbstractOrmMappingConverter;
import com.thucnh96.jpa.modal.Column;
import com.thucnh96.jpa.modal.Table;
import com.thucnh96.jpa.modal.payload.ProjectIto;
import com.thucnh96.jpa.utils.RawText;
import com.thucnh96.jpa.utils.SchemaUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import static com.thucnh96.jpa.constants.AppConstants.PATCH.*;

/**
 * @author : thucnh
 * @mailto : thucnh96.dev@gmail.com
 * @created :09/11/2021 - 10:03 AM
 */
public abstract class AbstractGenerate  {

    AbstractOrmMappingConverter abstractOrmMappingConverter;
    public AbstractGenerate(AbstractOrmMappingConverter abstractOrmMappingConverter){
        this.abstractOrmMappingConverter = abstractOrmMappingConverter;
    }



    protected String rawEntity() throws IOException {
        String text = RawText.rawTextCommon(AppConstants.PATH_ENTITY_RAW);
        return text;
    }
    protected String rawDao() throws IOException {
        String text = RawText.rawTextCommon(AppConstants.PATH_DAO_RAW);
        return text;
    }
    protected String rawResource() throws IOException {
        String text = RawText.rawTextCommon(AppConstants.PATH_RESOURCE_RAW);
        return text;
    }

    protected String rawSpec() throws IOException {
        String text = RawText.rawTextCommon(AppConstants.PATH_SPEC_RAW);
        return text;
    }

    protected String rawIto() throws IOException {
        String text = RawText.rawTextCommon(AppConstants.PATH_ITO_RAW);
        return text;
    }

    protected String rawDto() throws IOException {
        String text = RawText.rawTextCommon(AppConstants.PATH_DTO_RAW);
        return text;
    }

    protected String rawService() throws IOException {
        String text = RawText.rawTextCommon(AppConstants.PATH_SERVICE_RAW);
        return text;
    }
    protected String rawServiceImpl() throws IOException {
        String text = RawText.rawTextCommon(AppConstants.PATH_SERVICE_IMPL_RAW);
        return text;
    }

    protected Map<AppConstants.PATCH, String> getPackeges(ProjectIto projectIto){
        Map<AppConstants.PATCH, String> result = new HashMap<>();
        String entity =  projectIto.getPackages().concat(".").concat(projectIto.getEntityFolder());
        String service =  projectIto.getPackages().concat(".").concat(projectIto.getServiceFolder());
        String resource =  projectIto.getPackages().concat(".").concat(projectIto.getRestFolder());
        String spec =  projectIto.getPackages().concat(".").concat(projectIto.getSepcFolder());
        String dao =  projectIto.getPackages().concat(".").concat(projectIto.getRepositoryFolder());
        String base_to =  projectIto.getPackages().concat(".").concat(projectIto.getDtoFolder());
        result.put(ENTITY,entity);
        result.put(SERVICE,service);
        result.put(RESOURCE,resource);
        result.put(SPEC,spec);
        result.put(DAO,dao);
        result.put(BASE_TO,base_to);
        return result;
    }

    protected String rawFieldName(Table table) throws IOException {

        StringBuffer buffer = new StringBuffer();
        for (Column colum: table.getColums()){
            String columName = SchemaUtils.columName(colum.getDataType());
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
                    .append(abstractOrmMappingConverter.convertToJavaDataType(colum.getDataType()))
                    .append(" ")
                    .append(columName).append(" ;").append("\n").append("\n");

        }
        return  buffer.toString();

    }



}
