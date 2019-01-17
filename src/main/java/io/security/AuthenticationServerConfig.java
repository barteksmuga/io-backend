package io.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.approval.UserApprovalHandler;
import org.springframework.security.oauth2.provider.token.TokenStore;

import java.util.logging.Logger;

@Configuration
@EnableAuthorizationServer
public class AuthenticationServerConfig  extends AuthorizationServerConfigurerAdapter {

    private Logger logger= Logger.getLogger(getClass().getName());

    private static String REALM="CRM_REALM";
    private static int TEN_DAYS=60*60*24*10;
    private static int ONE_DAY=60*60*24;
    private static int THIRTHY_DAYS=60*60*24*30;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private UserApprovalHandler userApprovalHandler;


    @Autowired
    CrmUserDetailsService crmUserDetailsService;

    @Autowired
    PasswordEncoder passwordEncoder;



    //defines the security constraints on the token endpoint
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
//        security.passwordEncoder(passwordEncoder);
        security.realm(REALM);

    }

    //a configurer defines the client details service. Client details ca be initialized or you can just refer an
    //existing store
    //inMemory means that all the necessary data to create a session will be stored in memory
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        logger.info("authentication");
        clients.inMemory()
                .withClient("crmClient1")
                .secret("{noop}crmSuperSecret")
                .authorizedGrantTypes("password", "refresh-token")
                .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
                .scopes("read", "write", "trust")
                .accessTokenValiditySeconds(300)
                .refreshTokenValiditySeconds(THIRTHY_DAYS);
    }

    //defines the authorization and token endpoints and the token services

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        logger.info("authentication");
        endpoints.tokenStore(tokenStore).userApprovalHandler(userApprovalHandler)
                .authenticationManager(authenticationManager)
                .userDetailsService(crmUserDetailsService);
    }


}
