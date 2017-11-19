package com.example.Glasses.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

import javax.sql.DataSource;


@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final BasicAuthenticationEntryPoint entryPoint;
    private final AccessDeniedHandler handler;
    private final UserDetailsService userDetailsService;
    //private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final DataSource dataSource;

    @Value("${spring.queries.users-query}")
    private String usersQuery;

    @Value("${spring.queries.roles-query}")
    private String rolesQuery;

    @Autowired
    public SecurityConfig(BasicAuthenticationEntryPoint entryPoint,
                             AccessDeniedHandler handler, UserDetailsService userDetailsService,
                            // BCryptPasswordEncoder bCryptPasswordEncoder,
                          DataSource dataSource) {
        this.entryPoint = entryPoint;
        this.handler = handler;
        this.userDetailsService = userDetailsService;
        //this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.dataSource = dataSource;
    }

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.userDetailsService(userDetailsService);
    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth)
//            throws Exception {
//        auth
//                .jdbcAuthentication()
//                .usersByUsernameQuery(usersQuery)
//                .authoritiesByUsernameQuery(rolesQuery)
//                .dataSource(dataSource);
//                //.passwordEncoder(bCryptPasswordEncoder);
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/user").hasAuthority("ROLE_ADMIN")//.permitAll()
                .antMatchers("/admin/").hasAuthority("ROLE_ADMIN")
                // .antMatchers("/user/profile").hasRole(Authoritiy.USER.toString())
                // .antMatchers("/user/**").hasRole(Authoritiy.ADMIN.toString())
                .and()
                .httpBasic().authenticationEntryPoint(entryPoint)
                .and()
                .exceptionHandling().accessDeniedHandler(handler);
    }

}
