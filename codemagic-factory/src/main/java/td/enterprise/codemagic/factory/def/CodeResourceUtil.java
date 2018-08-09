package td.enterprise.codemagic.factory.def;

import java.util.ResourceBundle;

public class CodeResourceUtil {

    private static final ResourceBundle dbBundle = ResourceBundle.getBundle("database");
    private static final ResourceBundle confBundle = ResourceBundle.getBundle("config");

    public static String DIVER_NAME = "com.mysql.jdbc.Driver";
    public static String URL;
    public static String USERNAME = "root";
    public static String PASSWORD = "root";
    public static String DATABASE_NAME = "sys";
    public static String DATABASE_TYPE = "mysql";
    public static String DATABASE_TYPE_MYSQL;
    public static String DATABASE_TYPE_ORACLE;

    public static String web_root_package = "WebRoot";
    public static String source_root_package = "src/main/java";
    public static String resources_root_package = "src/main/resources";
    public static String test_root_package = "src/test/java";
    public static String corePackage = "td.enterprise.dmp.core";
    public static String restPackage = "td.enterprise.dmp.web";
    public static String dtoPackage = "td.enterprise.dmp";
    public static String corePackagePath = "td/enterprise/dmp/core";
    public static String restPackagePath = "td/enterprise/dmp/web";
    public static String dtoPackagePath = "td/enterprise/dmp";
    public static String bizAppPackage = "rfm";

    public static String BACKEND_ONLY;
    public static String TABLE_PREFIX;
    public static String MAPPER_XML_ONLY;
    public static String ENTITY_OPEN_TYPE;
    public static String ENTITY_SAVE_TYPE;
    public static String TABLE_ID;


    public static String CORE_PROJECT;
    public static String REST_PROJECT;
    public static String DTO_PROJECT="cdp-dto";
    public static String CLIENT_PROJECT;

    public static String BASE_CLASS_PACKAGE;
    public static String PAGE_GEN_TYPE;

    public static String SYSTEM_ENCODING;


    static {
        System.out.println("init properties start...");
        URL = "jdbc:mysql://localhost:3306/sys?useUnicode=true&characterEncoding=UTF-8";
        DATABASE_TYPE_MYSQL = "mysql";
        DATABASE_TYPE_ORACLE = "oracle";


        DIVER_NAME = dbBundle.getString("diver_name");
        URL = dbBundle.getString("url");
        USERNAME = dbBundle.getString("username");
        PASSWORD = dbBundle.getString("password");
        DATABASE_NAME = dbBundle.getString("database_name");

        corePackage = confBundle.getString("core_package");
        corePackagePath = corePackage.replace(".", "/");
        bizAppPackage = confBundle.getString("core_app_package");

        BACKEND_ONLY = confBundle.getString("backend_only");

        TABLE_PREFIX = confBundle.getString("table_prefix");

        MAPPER_XML_ONLY = confBundle.getString("mapper_xml_only");

        ENTITY_OPEN_TYPE = confBundle.getString("entity_open_type");

        ENTITY_SAVE_TYPE = confBundle.getString("entity_save_type");

        source_root_package = confBundle.getString("source_root_package");

        resources_root_package = confBundle.getString("resources_root_package");

        test_root_package = confBundle.getString("test_root_package");

        web_root_package = confBundle.getString("webroot_package");

        SYSTEM_ENCODING = confBundle.getString("system_encoding");

        TABLE_ID = confBundle.getString("table_id");

        CORE_PROJECT = confBundle.getString("core_project");
        REST_PROJECT = confBundle.getString("rest_project");
        CLIENT_PROJECT = confBundle.getString("client_project");

        BASE_CLASS_PACKAGE = confBundle.getString("base_class_package");
        PAGE_GEN_TYPE = (null == confBundle.getString("page_gen_type") ? "0" : confBundle.getString("page_gen_type"));

        if (URL.indexOf("mysql") >= 0 || URL.indexOf("MYSQL") >= 0)
            DATABASE_TYPE = DATABASE_TYPE_MYSQL;
        else if (URL.indexOf("oracle") >= 0 || URL.indexOf("ORACLE") >= 0)
            DATABASE_TYPE = DATABASE_TYPE_ORACLE;
        source_root_package = source_root_package.replace(".", "/");
        web_root_package = web_root_package.replace(".", "/");
        System.out.println("init properties success");

    }
}
