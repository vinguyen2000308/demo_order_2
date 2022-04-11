package com.example.demo_order_2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@PropertySource({"classpath:persistence-multiple-db.properties"})
@EnableJpaRepositories(
        basePackages = "com.example.demo_order_2.eventuate_repo",
        entityManagerFactoryRef = "eventuateMessageEntityManager",
        transactionManagerRef = "eventuateMessageTransactionManager"
)
public class PersistenceMessageConfiguration {


    @Autowired
    private Environment env;

    public PersistenceMessageConfiguration() {
        super();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean eventuateMessageEntityManager() {
        System.out.println("Load Eventuate Message Config... ");
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(eventuateMessageDataSource());
        em.setPackagesToScan("com.example.demo_order_2.domain.eventuate");
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
        em.setJpaPropertyMap(properties);
        return em;
    }

    @Bean
    public DataSource eventuateMessageDataSource() {

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("app.datasource.second.driver-class-name"));
        dataSource.setUrl(env.getProperty("app.datasource.second.url"));
        dataSource.setUsername(env.getProperty("app.datasource.second.username"));
        dataSource.setPassword(env.getProperty("app.datasource.second.password"));

        return dataSource;
    }

    @Primary
    @Bean
    public PlatformTransactionManager eventuateMessageTransactionManager() {

        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(eventuateMessageEntityManager().getObject());
        return transactionManager;
    }


}
