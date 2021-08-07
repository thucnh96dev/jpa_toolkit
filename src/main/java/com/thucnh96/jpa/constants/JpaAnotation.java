package com.thucnh96.jpa.constants;

/**
 * 
 * @author thucnh
 *
 */
public class JpaAnotation {
   
	// controller Anotation
    public static final String GetMapping = "@GetMapping";
    public static final String PostMapping = "@PostMapping";
    public static final String PutMapping = "@PutMapping";
    public static final String DeleteMapping = "@DeleteMapping";
    public static final String RestController = "@RestController";
    public static final String Controller = "@Controller";

    // entity Anotation
    public static final String Entity = "@Entity";
    public static final String Table = "@Table";
    public static final String Data = "@Data";
    public static final String NoArgsConstructor = "@NoArgsConstructor";
    public static final String AllArgsConstructor = "@AllArgsConstructor";
    public static final String Id = "@Id";
    public static final String GenerationType = "@GeneratedValue(strategy = GenerationType.IDENTITY)";
    public static final String Column = "@Column";
    public static final String TemporalType = "TemporalType.DATE";
    public static final String columnDefinition = "columnDefinition";

    // entity join Anotation
    public static final String JoinColumn = "@JoinColumn";
    public static final String joinColumns = "joinColumns";
    public static final String inverseJoinColumns = "inverseJoinColumns";
    public static final String ManyToMany = "@ManyToMany";
    public static final String OneToMany = "@OneToMany";
    public static final String ManyToOne = "@ManyToOne";
    public static final String FetchTypeLAZY = "fetch = FetchType.LAZY";
    public static final String unique = "unique";
    public static final String nullable = "nullable";
    public static final String pattern = "pattern";
    public static final String DateTimeFormat = "@DateTimeFormat";
    public static final String mappedBy = "@mappedBy";
    public static final String JsonIgnore = "@JsonIgnore";
    
    // service and request Anotation
    public static final String Service = "@Service";
    public static final String Override = "@Override";
    public static final String ModelAttribute = "@ModelAttribute";
    public static final String PathVariable = "@PathVariable";
    public static final String ResponseBody = "@ResponseBody";
    public static final String RequestBody = "@RequestBody";
    public static final String RequestMapping = "@RequestMapping";
    
    // import
    public static final String importLombok = "import lombok.*;";
    public static final String javaxPersistence = "import javax.persistence.*;";
    public static final String importJavaUtil = "import java.util.*;";
    public static final String importRepository = "import org.springframework.stereotype.Repository;";
    public static final String importJpaRepository = "import org.springframework.data.jpa.repository.JpaRepository;";
    public static final String importService = "import org.springframework.stereotype.Service;";
    public static final String importDataDomain= "import org.springframework.data.domain.*;";

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
