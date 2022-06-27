package com.example.camundaoauthresourceserverdemo.constants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CamundaPaths {

    @Value("${server.servlet.context-path}")
    private String contextPath;

    // seems we must initialise this in order to get Spring to pull from yml???
    public static String CAMUNDA_CONTEXT_PATH = "/hoohaa";

    @Value("${server.servlet.context-path}")
    public void setContextPathStatic(String path){
        Logger logger = LoggerFactory.getLogger(CamundaPaths.class);
        logger.info("Camunda context path: " + path);

        CamundaPaths.CAMUNDA_CONTEXT_PATH = path;
    }

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }
}
