package com.example.carriageservice.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

//todo: values from props
//todo: smth with db auth connection
@Configuration
@EnableReactiveMongoRepositories(value = "com.example.carriageservice.domain")
public class MongoConfig extends AbstractReactiveMongoConfiguration {

    @Bean
    public MongoClient mongoClient() {
        return MongoClients
                .create(new ConnectionString("mongodb://localhost:27017"));
    }

    @Override
    protected String getDatabaseName() {
        return "carriage";
    }

    @Override
    protected void configureClientSettings(MongoClientSettings.Builder builder) {
        MongoCredential credential = MongoCredential.createCredential("olfd", "carriage", "olfd".toCharArray());
        builder.credential(credential);
    }

    @Bean
    public ReactiveMongoTemplate reactiveMongoTemplate() {
        return new ReactiveMongoTemplate(mongoClient(), getDatabaseName());
    }
}
