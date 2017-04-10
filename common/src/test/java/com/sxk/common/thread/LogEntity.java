package com.sxk.common.thread;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class LogEntity {

    private Long   starttime;
    private String requestUrl;

    public Long getStarttime() {
        return starttime;
    }

    public void setStarttime(Long starttime) {
        this.starttime = starttime;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public LogEntity() {
        super();
    }

    public LogEntity(Long starttime, String requestUrl) {
        super();
        this.starttime = starttime;
        this.requestUrl = requestUrl;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

}
