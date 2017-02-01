package com.dangdang.common.util;

import java.util.ResourceBundle;

public class ConfigPropertiesUtil {

    private static final ResourceBundle bundle = ResourceBundle.getBundle("application");

    private static ConfigPropertiesUtil instance;

    private ConfigPropertiesUtil() {
    }

    public static ConfigPropertiesUtil getInstance() {
        if (instance == null) {
            synchronized (ConfigPropertiesUtil.class) {
                if (instance == null) {
                    instance = new ConfigPropertiesUtil();
                }
            }
        }
        return instance;
    }

    public String get(String key) {
        return bundle.containsKey(key) == true ? bundle.getString(key) : null;
    }

    public String get(String key, String defaultStr) {
        return bundle.containsKey(key) == true ? bundle.getString(key) : defaultStr;
    }

    public int get(String key, int defaultInteger) {
        return bundle.containsKey(key) == true ? Integer.parseInt(bundle.getString(key)) : defaultInteger;
    }

}
