package com.gewara.util;

import org.slf4j.LoggerFactory;

public class WebLogger {
    public static GewaLogger getLogger(Class<?> clazz) {
        return (GewaLogger) LoggerFactory.getLogger(clazz);
    }
}
