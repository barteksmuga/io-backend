package io;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:application.example.properties")
public class IoApplication {

    public static final String sourcePath = "/benie.DESKTOP-K69F1U6/source/repos/io-backend/data/";
    private final int MAX_FILE_UPLOAD_SIZE = 10 * 1024 * 1024; // 10 MB
    public static void main (String[] args) {
        SpringApplication.run(IoApplication.class, args);
    }

    @Bean
    public TomcatServletWebServerFactory containerFactory () {
        return new TomcatServletWebServerFactory() {
            protected void customizeConnector (Connector connector) {
                super.customizeConnector(connector);
                connector.setMaxPostSize(MAX_FILE_UPLOAD_SIZE);
                connector.setMaxSavePostSize(MAX_FILE_UPLOAD_SIZE);
                if (connector.getProtocolHandler() instanceof AbstractHttp11Protocol) {
                    ((AbstractHttp11Protocol) connector.getProtocolHandler()).setMaxSwallowSize(MAX_FILE_UPLOAD_SIZE);
                    logger.info(String.format("SET MaxSwallowSize %d", MAX_FILE_UPLOAD_SIZE));
                }
            }
        };
    }
}
