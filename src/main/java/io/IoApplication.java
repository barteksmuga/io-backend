package io;

import io.security.CustomerUserDetails;
import io.repositories.UserRepository;
import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;



//todo wykorzystac projekt crm=oauth2 do stowrzenia autentykacji
//todo projekt ten robiny jest na bazie h2 ktora nie dziala ale w opisie z tutorialu czesc 4 mozna wszystko wywnioskowac
//todo Tokeny bedą przechowywane w Pamieci inMemory a uzytkownik i haslo w bazie danych


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


//    @Autowired
//    public void authenticationManager(AuthenticationManagerBuilder builder, UserRepository repo) throws Exception{
//
//        builder.userDetailsService(new UserDetailsService() {
//            @Override
//            public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
//                return new CustomerUserDetails(repo.findUserByEmail(s));
//            }
//        });
//    }
}
