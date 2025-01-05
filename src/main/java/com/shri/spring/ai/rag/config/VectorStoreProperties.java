package com.shri.spring.ai.rag.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.util.List;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "ai.app")
public class VectorStoreProperties {

    private String vectorStoreDir;
    private List<Resource> document2load;
}
