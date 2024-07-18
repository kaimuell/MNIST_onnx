package com.kaimuellercode.onnx_demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class OnnxDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnnxDemoApplication.class, args);
    }
}
