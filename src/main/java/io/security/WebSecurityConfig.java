package io.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenStoreUserApprovalHandler;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;


import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.logging.Logger;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    private Logger logger= Logger.getLogger(getClass().getName());
    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private CrmUserDetailsService crmUserDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


    @PostConstruct
    public void init(){crmUserDetailsService.setPasswordEncoder(passwordEncoder());}


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    public void configure(HttpSecurity http) throws Exception{



        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("user/signup").permitAll()
                .antMatchers("/oath/token").permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .realmName("CRM_REALM");
    }


    protected void configure(AuthenticationManagerBuilder auth) throws Exception{

        auth.userDetailsService(crmUserDetailsService)
                .passwordEncoder(passwordEncoder())
                .and()
                .authenticationProvider(authProvider())
                .jdbcAuthentication()
                .dataSource(dataSource);
//        auth.userDetailsService(crmUserDetailsService)
//                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public TokenStore tokenStore(){
        return new InMemoryTokenStore();
    }


    @Bean
    @Autowired
    public TokenStoreUserApprovalHandler userApprovalHandler(TokenStore tokenStore){
        logger.info("authentication");
        TokenStoreUserApprovalHandler handler=new TokenStoreUserApprovalHandler();
        handler.setTokenStore(tokenStore);
        handler.setRequestFactory(new DefaultOAuth2RequestFactory(clientDetailsService));
        handler.setClientDetailsService(clientDetailsService);
        return handler;
    }

    @Bean
    @Autowired
    public ApprovalStore approvalStore(TokenStore tokenStore) throws Exception{
        logger.info("authentication");
        TokenApprovalStore store=new TokenApprovalStore();
        store.setTokenStore(tokenStore);
        return store;
    }

    @Bean
    public DaoAuthenticationProvider authProvider(){
        logger.info("authentication");
        final CustomAuthenticationProvider authProvider=new CustomAuthenticationProvider();
        authProvider.setUserDetailsService(crmUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

}
