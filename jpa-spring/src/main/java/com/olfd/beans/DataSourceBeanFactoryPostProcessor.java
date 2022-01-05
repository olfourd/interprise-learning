package com.olfd.beans;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

//@Component
@Slf4j
public class DataSourceBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        BeanDefinition dataSourceDefinition = beanFactory.getBeanDefinition("dataSource");

        log.info("-----------------------------------------------------------");
        log.info("Custom bean factory post processor");
        log.info("Bean class: {}", dataSourceDefinition.getBeanClassName());
        log.info("Factory bean name: {}", dataSourceDefinition.getFactoryBeanName());
        log.info("Description: {}", dataSourceDefinition.getDescription());

        MutablePropertyValues propertyValues = dataSourceDefinition.getPropertyValues();
        log.info("PropertyValues: {}", propertyValues);
        if (propertyValues.contains("login")) {
            propertyValues.addPropertyValue("login", "new login from BFPP");
        }

        String currentScope = dataSourceDefinition.getScope();
        log.info("Scope: {}", currentScope);
        String newPrototype = "prototype";
        dataSourceDefinition.setScope(newPrototype);
        log.info("Prototype changed from {} to {}", currentScope, newPrototype);

        log.info("-----------------------------------------------------------");
    }
}
