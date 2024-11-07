package com.jiuaoedu.evaluation.process_controller;

import java.util.Objects;


public class MappingInfo {
    private String url;
    private String httpMethod;

    public MappingInfo(String url, String httpMethod) {
        this.url = url;
        this.httpMethod = httpMethod;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MappingInfo that = (MappingInfo) o;
        return Objects.equals(getUrl(), that.getUrl()) && Objects.equals(getHttpMethod(), that.getHttpMethod());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUrl(), getHttpMethod());
    }
}
