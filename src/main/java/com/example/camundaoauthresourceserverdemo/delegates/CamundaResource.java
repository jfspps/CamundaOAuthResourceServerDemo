package com.example.camundaoauthresourceserverdemo.delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;

@Named
public class CamundaResource implements JavaDelegate {

    private final Logger logger = LoggerFactory.getLogger(CamundaResource.class);

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        logger.debug("Access to restricted resource was granted by OAuth authorisation server...");

        delegateExecution.setVariable("restricted_resource_variable", "Looks like OAuth let you in...here comes the surprise package...");
        logger.debug("Username entered: " + delegateExecution.getVariable("username"));

        logger.debug("Process variable \"restricted_resource_variable\" assigned. End of service task.");
    }
}
