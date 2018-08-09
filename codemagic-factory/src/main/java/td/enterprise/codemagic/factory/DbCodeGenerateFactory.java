package td.enterprise.codemagic.factory;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.VelocityContext;

import td.enterprise.codemagic.factory.bean.ColumnData;
import td.enterprise.codemagic.factory.bean.DbCreateBean;
import td.enterprise.codemagic.factory.def.CodeResourceUtil;
import td.enterprise.codemagic.factory.def.FtlDef;

public class DbCodeGenerateFactory {

    private static final Log log = LogFactory.getLog(DbCodeGenerateFactory.class);
    private static String url;
    private static String username;
    private static String passWord;
    private static String core_package;
    private static String rest_package;
    private static String web_package;
    private static String dto_package;

    public DbCodeGenerateFactory() {
    }

    static {
        url = CodeResourceUtil.URL;
        username = CodeResourceUtil.USERNAME;
        passWord = CodeResourceUtil.PASSWORD;
        core_package = CodeResourceUtil.corePackage;
        rest_package = CodeResourceUtil.restPackage;
        dto_package = CodeResourceUtil.dtoPackage;
    }


    public static void codeGenerate(String codeGeneratePath, String tableName, String codeName, String entityPackage, String keyType) {
        DbCreateBean createBean = new DbCreateBean();
        createBean.setMysqlInfo(url, username, passWord);
        String className = createBean.formateClassName(tableName);
        String lowerName = (new StringBuilder(String.valueOf(className.substring(0, 1).toLowerCase()))).append(className.substring(1, className.length()))
                .toString();
        String lowerCaseName = className.toLowerCase();
        if (lowerCaseName.contains(entityPackage.toLowerCase())) {
            lowerCaseName = lowerCaseName.replace(entityPackage.toLowerCase(), "");
        }
        //D:/git/td/codeTest/dmp-core
        String coreProjectPath = codeGeneratePath + "/" + CodeResourceUtil.CORE_PROJECT;
        String restProjectPath = codeGeneratePath + "/" + CodeResourceUtil.REST_PROJECT;
        String dtoProjectPath = codeGeneratePath + "/" + CodeResourceUtil.DTO_PROJECT;
        String clientProjectPath = codeGeneratePath + "/" + CodeResourceUtil.CLIENT_PROJECT;
        //D:/git/td/codeTest/dmp-core/src/main/java/td/enterprise/dmp/core
        String coreProjectSrcPath = coreProjectPath + "/" + CodeResourceUtil.source_root_package + "/" + CodeResourceUtil.corePackagePath;
        String coreProjectResourcesPath = coreProjectPath + "/" + CodeResourceUtil.resources_root_package + "/" + CodeResourceUtil.corePackagePath;
        String restProjectSrcPath = restProjectPath + "/" + CodeResourceUtil.source_root_package + "/" + CodeResourceUtil.restPackagePath;
        String dtoProjectSrcPath = dtoProjectPath + "/" + CodeResourceUtil.source_root_package + "/" + CodeResourceUtil.dtoPackagePath;

        String coreProjectTestPath = coreProjectPath + "/" + CodeResourceUtil.test_root_package + "/" + CodeResourceUtil.corePackagePath;
        String restProjectTestPath = restProjectPath + "/" + CodeResourceUtil.test_root_package + "/" + CodeResourceUtil.restPackagePath;

        String coreProjectWebappPath = coreProjectPath + "/" + CodeResourceUtil.web_root_package + "/" + CodeResourceUtil.corePackagePath;
        String restProjectWebappPath = restProjectPath + "/" + CodeResourceUtil.web_root_package + "/" + CodeResourceUtil.corePackagePath;
        String clientProjectWebappPath = clientProjectPath + "/" + CodeResourceUtil.web_root_package;

        String clientProjectWebappJsPath = clientProjectWebappPath + "/js/app";
        String clientProjectWebappHtmlPath = clientProjectWebappPath + "/html/" + entityPackage;
        String clientProjectWebappCssPath = clientProjectWebappPath + "/css/" + entityPackage;


        String modelPath = "/page/" + entityPackage + "/" + className + "Page.java";
        String beanPath = "/entity/" + entityPackage + "/" + className + ".java";
        String dtoResPath = "/dto/" + entityPackage + "/" + className + "Response.java";
        String dtoReqPath = "/dto/" + entityPackage + "/" + className + "Request.java";
        String dtoListReqPath = "/dto/" + entityPackage + "/" + className + "ListRequest.java";
        String mapperPath = "/dao/" + entityPackage + "/" + className + "Dao.java";
        String servicePath = "/service/" + entityPackage + "/" + className + "Service.java";
        String serviceJUnitPath = "/service/" + entityPackage + "/" + className + "ServiceTest.java";
        String controllerPath = "/controller/" + entityPackage + "/" + className + "Controller.java";
        String sqlMapperPath = "/mapper/" + entityPackage + "/" + className + "Mapper.xml";

        String listWidgetHtmlPath = "/list/" + className + "List.html";
        String formWidgetHtmlPath = "/form/" + className + "Form.html";
        String searchWidgetHtmlPath = "/serach/" + className + "Search.html";
        String dialogWidgetHtmlPath = "/dialog/" + className + "Dialog.html";

        String listControllerJsPath = "/controllers/" + entityPackage + "/" + className + "ListController.js";
        String formControllerJsPath = "/controllers/" + entityPackage + "/" + className + "FormController.js";

        String serviceJsPath = "/services/" + entityPackage + "/" + className + "Service.js";

        String stateJsPath = "/states/" + entityPackage + "/" + className + "State.js";


        String listWidgetName = className + "List";
        String formWidgetName = className + "Form";
        String searchbarWidgetName = className + "SearchBar";
        String dialogWidgetName = className + "Dialog";

        String serviceConfigName = lowerName + "Service";
        String formConfigName = lowerName + "Form";
        String searchbarAction = (searchbarWidgetName + "Action").toLowerCase();
        String upperPrimaryKey = createBean.formateName(CodeResourceUtil.TABLE_ID);
        String primaryKey = upperPrimaryKey.substring(0, 1).toLowerCase() + upperPrimaryKey.substring(1, upperPrimaryKey.length());
        String lowerNames = lowerName + "s";
        if (lowerName.endsWith("y")) {
            lowerNames = lowerName.substring(0, lowerName.length() - 1) + "ies";
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = format.format(Calendar.getInstance().getTime());

        VelocityContext context = new VelocityContext();
        context.put("className", className);
        context.put("lowerName", lowerName);
        context.put("codeName", codeName);
        context.put("tableName", tableName);
        context.put("bussPackage", core_package);
        context.put("restPackage", rest_package);
        context.put("dtoPackage", dto_package);
        context.put("bizAppPackage", CodeResourceUtil.bizAppPackage);
        /**
         * 设置dto路径
         */
        context.put("entityPackage", entityPackage);
        context.put("keyType", keyType);
        context.put("upperPrimaryKey", upperPrimaryKey);
        context.put("primaryKey", primaryKey);
        context.put("entityOpenType", CodeResourceUtil.ENTITY_OPEN_TYPE);
        context.put("entitySaveType", CodeResourceUtil.ENTITY_SAVE_TYPE);

        context.put("listWidgetName", listWidgetName);
        context.put("formWidgetName", formWidgetName);
        context.put("searchbarWidgetName", searchbarWidgetName);
        context.put("dialogWidgetName", dialogWidgetName);


        context.put("serviceConfigName", serviceConfigName);
        context.put("formConfigName", formConfigName);
        context.put("searchbarAction", searchbarAction);

        context.put("baseClassPackage", CodeResourceUtil.BASE_CLASS_PACKAGE);
        context.put("lowerNames", lowerNames);
        context.put("lowerCaseName", lowerCaseName);
        context.put("currentDate", currentDate);

        context.put("dl", "$");


        List<ColumnData> columnDatas = new ArrayList<ColumnData>();
        try {
            columnDatas = createBean.getColumnDatas(tableName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        context.put("entityImportClasses", createBean.getEntityImportClasses(columnDatas));

        try {
            Map<String, Object> sqlMap = createBean.getAutoCreateSql(tableName);
            context.put("columnDatas", createBean.getColumnDatas(tableName));
            context.put("SQL", sqlMap);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        if (CodeResourceUtil.PAGE_GEN_TYPE.equals("1")) {
            CommonPageParser.WriterPage(context, "java/MapperTemplate.ftl", coreProjectResourcesPath, sqlMapperPath);
        } else if (CodeResourceUtil.PAGE_GEN_TYPE.equals("2")) {
            CommonPageParser.WriterPage(context, "java/MapperTemplate.ftl", coreProjectResourcesPath, sqlMapperPath);
            CommonPageParser.WriterPage(context, "java/EntityTemplate.ftl", coreProjectSrcPath, beanPath);
            CommonPageParser.WriterPage(context, "java/PageTemplate.ftl", coreProjectSrcPath, modelPath);
            CommonPageParser.WriterPage(context, "java/DaoTemplate.ftl", coreProjectSrcPath, mapperPath);
        } else if (CodeResourceUtil.PAGE_GEN_TYPE.equals("3")) {
            CommonPageParser.WriterPage(context, "java/MapperTemplate.ftl", coreProjectResourcesPath, sqlMapperPath);
            CommonPageParser.WriterPage(context, "java/EntityTemplate.ftl", coreProjectSrcPath, beanPath);
            CommonPageParser.WriterPage(context, "java/PageTemplate.ftl", coreProjectSrcPath, modelPath);
            CommonPageParser.WriterPage(context, "java/DaoTemplate.ftl", coreProjectSrcPath, mapperPath);
            CommonPageParser.WriterPage(context, "java/ServiceTemplate.ftl", coreProjectSrcPath, servicePath);
            CommonPageParser.WriterPage(context, "junit/ServiceJunitTemplate.ftl", coreProjectTestPath, serviceJUnitPath);
        } else if (CodeResourceUtil.PAGE_GEN_TYPE.equals("4")) {
            CommonPageParser.WriterPage(context, "java/ControllerTemplate.ftl", restProjectSrcPath, controllerPath);
        } else if (CodeResourceUtil.PAGE_GEN_TYPE.equals("5")) {
            CommonPageParser.WriterPage(context, "html/dialog/Dialog.html.ftl", clientProjectWebappHtmlPath, dialogWidgetHtmlPath);
            CommonPageParser.WriterPage(context, "html/form/Form.html.ftl", clientProjectWebappHtmlPath, formWidgetHtmlPath);
            CommonPageParser.WriterPage(context, "html/list/List.html.ftl", clientProjectWebappHtmlPath, listWidgetHtmlPath);

            CommonPageParser.WriterPage(context, "js/controller/ListController.js.ftl", clientProjectWebappJsPath, listControllerJsPath);
            CommonPageParser.WriterPage(context, "js/controller/FormController.js.ftl", clientProjectWebappJsPath, formControllerJsPath);
            CommonPageParser.WriterPage(context, "js/service/Service.js.ftl", clientProjectWebappJsPath, serviceJsPath);
            CommonPageParser.WriterPage(context, "js/state/State.js.ftl", clientProjectWebappJsPath, stateJsPath);
        } else if (CodeResourceUtil.PAGE_GEN_TYPE.equals("6")) {
            CommonPageParser.WriterPage(context, "java/MapperTemplate.ftl", coreProjectResourcesPath, sqlMapperPath);
            CommonPageParser.WriterPage(context, "java/EntityTemplate.ftl", coreProjectSrcPath, beanPath);
            CommonPageParser.WriterPage(context, "java/PageTemplate.ftl", coreProjectSrcPath, modelPath);
            CommonPageParser.WriterPage(context, "java/DaoTemplate.ftl", coreProjectSrcPath, mapperPath);
            CommonPageParser.WriterPage(context, "java/ServiceTemplate.ftl", coreProjectSrcPath, servicePath);
            CommonPageParser.WriterPage(context, "junit/ServiceJunitTemplate.ftl", coreProjectTestPath, serviceJUnitPath);

            CommonPageParser.WriterPage(context, "java/ControllerTemplate.ftl", restProjectSrcPath, controllerPath);

            CommonPageParser.WriterPage(context, "html/dialog/Dialog.html.ftl", clientProjectWebappHtmlPath, dialogWidgetHtmlPath);
            CommonPageParser.WriterPage(context, "html/form/Form.html.ftl", clientProjectWebappHtmlPath, formWidgetHtmlPath);
            CommonPageParser.WriterPage(context, "html/list/List.html.ftl", clientProjectWebappHtmlPath, listWidgetHtmlPath);

            CommonPageParser.WriterPage(context, "js/controller/ListController.js.ftl", clientProjectWebappJsPath, listControllerJsPath);
            CommonPageParser.WriterPage(context, "js/controller/FormController.js.ftl", clientProjectWebappJsPath, formControllerJsPath);
            CommonPageParser.WriterPage(context, "js/service/Service.js.ftl", clientProjectWebappJsPath, serviceJsPath);
            CommonPageParser.WriterPage(context, "js/state/State.js.ftl", clientProjectWebappJsPath, stateJsPath);
        } else if (CodeResourceUtil.PAGE_GEN_TYPE.equals("0")) {
            CommonPageParser.WriterPage(context, "java/MapperTemplate.ftl", coreProjectResourcesPath, sqlMapperPath);
            CommonPageParser.WriterPage(context, "java/EntityTemplate.ftl", coreProjectSrcPath, beanPath);
            CommonPageParser.WriterPage(context, "java/EntityRequestTemplate.ftl", dtoProjectSrcPath, dtoReqPath);
            CommonPageParser.WriterPage(context, "java/PageTemplate.ftl", dtoProjectSrcPath, dtoListReqPath);
            CommonPageParser.WriterPage(context, "java/ResponseTemplate.ftl", dtoProjectSrcPath, dtoResPath);
            CommonPageParser.WriterPage(context, "java/DaoTemplate.ftl", coreProjectSrcPath, mapperPath);
            CommonPageParser.WriterPage(context, "java/ServiceTemplate.ftl", coreProjectSrcPath, servicePath);
            //CommonPageParser.WriterPage(context, "junit/ServiceJunitTemplate.ftl", coreProjectTestPath, serviceJUnitPath);

            CommonPageParser.WriterPage(context, "java/ControllerTemplate.ftl", restProjectSrcPath, controllerPath);

        } else {
            log.warn("不支持的code_gen_type : " + CodeResourceUtil.PAGE_GEN_TYPE);
        }
        log.info("----------------------------代码生成完毕---------------------------");
    }


}
