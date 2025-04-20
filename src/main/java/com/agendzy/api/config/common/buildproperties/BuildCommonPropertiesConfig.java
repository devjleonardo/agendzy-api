package com.agendzy.api.config.common.buildproperties;

import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class BuildCommonPropertiesConfig {

    @Bean
    public BuildProperties buildProperties() {
        Properties properties = new Properties();
        properties.put("name", "Agendizy");
        properties.put("version", "1.0.0");
        properties.put("time", System.currentTimeMillis());

        return new BuildProperties(properties);
    }

}
