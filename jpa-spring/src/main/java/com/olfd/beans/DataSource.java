package com.olfd.beans;

import com.olfd.aop.AnyService;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

@RequiredArgsConstructor
@Slf4j
@Data
public class DataSource implements DisposableBean {

    private final String url;
    private final String login;
    private final String password;

    private Environment environment;
    private AnyService anyService;

    @Autowired
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Autowired
    public void setAnyService(AnyService anyService) {
        this.anyService = anyService;
    }

    @PreDestroy
    private void preDestroy() {
      log.info("@PreDestroy");
    }


    @Override
    public void destroy() throws Exception {
        log.info("DisposableBean.destroy()");
    }

    private void destroyMethod() {
        log.info("bean destroy method");
    }

}
