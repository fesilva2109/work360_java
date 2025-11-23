package com.project.Work360.config;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties; // IMPORTANTE
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages = {
        "com.project.Work360.repository", 
        "com.project.Work360.ai.rag"
    },
    entityManagerFactoryRef = "azureEntityManagerFactory",
    transactionManagerRef = "azureTransactionManager"
)
public class AzureConfig {

    @Primary
    @Bean
    @ConfigurationProperties("spring.datasource.azure")
    public DataSourceProperties azureDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean(name = "azureDataSource")
    public DataSource azureDataSource() {
        return azureDataSourceProperties()
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Primary
    @Bean(name = "azureEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean azureEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("azureDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages(
                    "com.project.Work360.model",   
                    "com.project.Work360.ai.rag"
                )
                .persistenceUnit("azure")
                .build();
    }

    @Primary
    @Bean(name = "azureTransactionManager")
    public PlatformTransactionManager azureTransactionManager(
            @Qualifier("azureEntityManagerFactory") EntityManagerFactory azureEntityManagerFactory) {
        return new JpaTransactionManager(azureEntityManagerFactory);
    }
}