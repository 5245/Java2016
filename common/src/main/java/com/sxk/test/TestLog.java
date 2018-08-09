package com.sxk.test;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.util.StatusPrinter;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLog {
    static {
        initLog();
    }

    //logback 日志配置初始化，避免打印垃圾日志
    private static void initLog() {
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        lc.reset();
        JoranConfigurator configurator = new JoranConfigurator();
        configurator.setContext(lc);
        try {
            configurator.doConfigure("src/main/resources/conf/logback.xml");
        } catch (Exception e) {
            e.printStackTrace();
        }
        StatusPrinter.printInCaseOfErrorsOrWarnings(lc);
    }

    public static final Logger log = LoggerFactory.getLogger(TestLog.class);

    public static void main(String[] args) {
        User u = User.builder().id(1).name("a").money(100000L).build();
        log.debug(u.toString());
        log.debug("uStr:{}", u);
        log.info("uStr:{}", u);
    }
}

@Setter
@Getter
@Builder
class User {
    int id;
    String name;
    Long money;

    @Override
    public String toString() {
        System.out.println("toString");
        return ToStringBuilder.reflectionToString(this);
    }
}