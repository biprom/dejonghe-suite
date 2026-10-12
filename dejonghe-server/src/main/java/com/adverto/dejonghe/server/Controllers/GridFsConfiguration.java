package com.adverto.dejonghe.server.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

@Configuration
public class GridFsConfiguration {

    @Bean
    public GridFsTemplate gridFsTemplate(
            MongoDatabaseFactory mongoDatabaseFactory,
            MappingMongoConverter mongoConverter) {

        return new GridFsTemplate(
                mongoDatabaseFactory,
                mongoConverter
        );
    }
}
