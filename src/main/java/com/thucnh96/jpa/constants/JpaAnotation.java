package com.thucnh96.jpa.constants;

/**
 * 
 * @author thucnh
 *
 */
public class JpaAnotation {

    // core
    public static final String packageStr = "package ";
    public static final String nextLine = "\n";

	// controller Anotation
    public static final String GetMapping = "@GetMapping\n";
    public static final String PostMapping = "@PostMapping\n";
    public static final String PutMapping = "@PutMapping\n";
    public static final String DeleteMapping = "@DeleteMapping\n";
    public static final String RestController = "@RestController\n";
    public static final String RequestMapping = "@RequestMapping";
    public static final String Controller = "@Controller\n";

    // entity Anotation
    public static final String Entity = "@Entity\n";
    public static final String Table = "@Table\n";
    public static final String Data = "@Data\n";
    public static final String NoArgsConstructor = "@NoArgsConstructor\n";
    public static final String AllArgsConstructor = "@AllArgsConstructor\n";
    public static final String Id = "@Id\n";
    public static final String GenerationType = "@GeneratedValue(strategy = GenerationType.IDENTITY)\n";
    public static final String Column = "@Column";
    public static final String TemporalType = "TemporalType.DATE\n";
    public static final String columnDefinition = "columnDefinition\n";

    // entity join Anotation
    public static final String JoinColumn = "@JoinColumn\n";
    public static final String joinColumns = "joinColumns\n";
    public static final String inverseJoinColumns = "inverseJoinColumns\n";
    public static final String ManyToMany = "@ManyToMany\n";
    public static final String OneToMany = "@OneToMany\n";
    public static final String ManyToOne = "@ManyToOne\n";
    public static final String FetchTypeLAZY = "fetch = FetchType.LAZY\n";
    public static final String unique = "unique\n";
    public static final String nullable = "nullable\n";
    public static final String pattern = "pattern\n";
    public static final String DateTimeFormat = "@DateTimeFormat\n";
    public static final String mappedBy = "@mappedBy\n";
    public static final String JsonIgnore = "@JsonIgnore\n";
    
    // service and request Anotation
    public static final String Service = "@Service\n";
    public static final String Override = "@Override\n";
    public static final String ModelAttribute = "@ModelAttribute";
    public static final String PathVariable = "@PathVariable\n";
    public static final String ResponseBody = "@ResponseBody\n";
    public static final String RequestBody = "@RequestBody";
    public static final String Autowired = " @Autowired\n";

    // import
    public static final String importStr = "import ";
    public static final String importLombok = "import lombok.*;\n";
    public static final String importSpringframeworkUtil = "import org.springframework.util.*;\n";
    public static final String javaxPersistence = "import javax.persistence.*;\n";
    public static final String javaxPersistenceCriteria = "import javax.persistence.criteria.*;\n";
    public static final String importJavaUtil = "import java.util.*;\n";
    public static final String importRepository = "import org.springframework.stereotype.Repository;\n";
    public static final String importJpaRepository = "import org.springframework.data.jpa.repository.JpaRepository;\n";
    public static final String importService = "import org.springframework.stereotype.Service;\n";
    public static final String importDataDomain= "import org.springframework.data.domain.*;\n";
    public static final String importController= "import org.springframework.stereotype.Controller;\n";
    public static final String importWebBindAnnotation= "import org.springframework.web.bind.annotation.*;\n";
    public static final String importHttpServletRequest= "import org.springframework.http.*;\n";
    public static final String importServletHttp= "import javax.servlet.http.HttpServletRequest;\n";
    public static final String importAutowired= "import org.springframework.beans.factory.annotation.Autowired;\n";



    // data type
    public static final String BigDecimal= "BigDecimal";
    public static final String FLOAT= "FLOAT";
    public static final String DOUBLE= "DOUBLE";
    public static final String NUMERIC= "NUMERIC";
    public static final String DATETIME= "DATETIME";
    public static final String CHAR= "CHAR";
    public static final String VARCHAR= " VARCHAR";
    public static final String TEXT= "TEXT";
    public static final String BIT1= "BIT(1)";

    public static  enum FetchType {
        LAZY,
        EAGER;
    }
}
