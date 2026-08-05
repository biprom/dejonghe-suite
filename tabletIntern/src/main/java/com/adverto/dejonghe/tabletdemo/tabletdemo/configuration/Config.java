package com.adverto.dejonghe.tabletdemo.tabletdemo.configuration;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Config {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public GridFSBucket gridFSBucket(
            MongoDatabaseFactory mongoDatabaseFactory
    ) {

        MongoDatabase database =
                mongoDatabaseFactory.getMongoDatabase();

        return GridFSBuckets.create(database);
    }
}


