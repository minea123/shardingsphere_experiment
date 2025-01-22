package com.example.demo;

import java.io.IOException;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.apache.shardingsphere.driver.api.yaml.YamlShardingSphereDataSourceFactory;


@Configuration
public class AppConfig {
    @Value("classpath:shard.yaml")
    private Resource shardYamlFile;

    @Bean
    DataSource shardingSphereDataSource() throws SQLException, IOException {
        return YamlShardingSphereDataSourceFactory.createDataSource(shardYamlFile.getFile());
    }

    @Bean
    JdbcTemplate shardingSphereJdbcTemplate(DataSource shardingSphereDataSource) {
        return new JdbcTemplate(shardingSphereDataSource);
    }
}
