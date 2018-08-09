package td.enterprise.codemagic.factory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

public class CommonPageParser {

    private static VelocityEngine ve;
    private static final String CONTENT_ENCODING = "UTF-8";
    private static final Log log;
    private static boolean isReplace = true;

    public CommonPageParser() {
    }

    public static void WriterPage(VelocityContext context, String templateName, String fileDirPath, String targetFile) {
        try {
            File file = new File((new StringBuilder(String.valueOf(fileDirPath))).append(targetFile).toString());
            if (!file.exists())
                (new File(file.getParent())).mkdirs();
            else if (isReplace)
                log.info((new StringBuilder("替换文件:")).append(file.getAbsolutePath()).toString());
            Template template = ve.getTemplate(templateName, "UTF-8");
            FileOutputStream fos = new FileOutputStream(file);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));
            template.merge(context, writer);
            writer.flush();
            writer.close();
            fos.close();
            log.info((new StringBuilder("生成文件：")).append(file.getAbsolutePath()).toString());
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
        }
    }

    static {
        log = LogFactory.getLog(CommonPageParser.class);
        try {
            String path = CommonPageParser.class.getClassLoader().getResource("config.properties").getPath();
            String templateBasePath = path.replace("config.properties", "templates");


            System.out.println("templateBasePath:" + templateBasePath);
            Properties properties = new Properties();
            properties.setProperty("resource.loader", "file");
            properties.setProperty("file.resource.loader.description", "Velocity File Resource Loader");
            properties.setProperty("file.resource.loader.path", templateBasePath);
            properties.setProperty("file.resource.loader.cache", "true");
            properties.setProperty("file.resource.loader.modificationCheckInterval", "30");
            properties.setProperty("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.Log4JLogChute");
            properties.setProperty("runtime.log.logsystem.log4j.logger", "org.apache.velocity");
            properties.setProperty("directive.set.null.allowed", "true");
            VelocityEngine velocityEngine = new VelocityEngine();
            velocityEngine.init(properties);
            ve = velocityEngine;
        } catch (Exception e) {
            log.error(e);
        }
    }
}
