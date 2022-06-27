package com.example.camundaoauthresourceserverdemo.multitenancy;

import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.context.Context;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;
import org.camunda.bpm.engine.impl.interceptor.CommandInterceptor;
import org.camunda.bpm.engine.spring.SpringProcessEngineConfiguration;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Collection;

/**
 * https://docs.camunda.org/manual/7.10/user-guide/spring-framework-integration/configuration/#using-spring-javaconfig
 */
@Configuration
public class CustomCamundaProcessEngineConfiguration {

    @Value("${spring.datasource.url}")
    private String dataSourceUrl;

    @Value("${spring.datasource.username}")
    private String dataSourceUsername;

    @Value("${spring.datasource.password}")
    private String dataSourcePassword;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String dataSourceSchemaUpdate;

    @Value("${spring.datasource.driver-class-name}")
    private String dataSourceDriverClassName;

    @Value("${camunda.bpm.history-level}")
    private String camundaBpmnHistoryLevel;

    @Bean
    public DataSource dataSource() {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();

        // need to set the Maven dependency such that it is not restricted to runtime scope only
        dataSourceBuilder
                .driverClassName(dataSourceDriverClassName)
                .url(dataSourceUrl)
                .username(dataSourceUsername)
                .password(dataSourcePassword);

        return dataSourceBuilder.build();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public SpringProcessEngineConfiguration processEngineConfiguration() {
        SpringProcessEngineConfiguration config = new SpringProcessEngineConfiguration();

        // have the tenant Id set automatically based authenticated tenant
        config.setTenantIdProvider(new CustomTenantIdProvider());

        config.setDatabaseType("mysql");
        config.setDataSource(dataSource());
        config.setTransactionManager(transactionManager());

        config.setDatabaseSchemaUpdate(dataSourceSchemaUpdate);
        config.setHistory(camundaBpmnHistoryLevel);

        return config;
    }
}
