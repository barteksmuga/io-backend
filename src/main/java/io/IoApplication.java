package io;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import java.io.IOException;

@SpringBootApplication
public class IoApplication {

    public static void main (String[] args) {
        SpringApplication.run(IoApplication.class, args);
    }

    @Bean
    public CommonsMultipartResolver getResolver() throws IOException {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        //Set the maximum allowed size (in bytes) for each individual file.
        resolver.setMaxUploadSizePerFile(5242880);//5MB
        //You may also set other available properties.
        return resolver;
    }

    public static final String sourcePath = "/Users/Bartek/studia/io/data/";
}
