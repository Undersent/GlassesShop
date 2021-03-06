//package com.example.Glasses.Configuration;
//
//
//import java.util.Arrays;
//import java.util.Collections;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
//import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
//
//@Configuration
//@EnableAuthorizationServer
//@EnableResourceServer
//public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
//
//    @Value("${security.jwt.client-id}")
//    private String clientId;
//
//    @Value("${security.jwt.client-secret}")
//    private String clientSecret;
//
//    @Value("${security.jwt.grant-type}")
//    private String grantType;
//
//    @Value("${security.jwt.scope-read}")
//    private String scopeRead;
//
//    @Value("${security.jwt.scope-write}")
//    private String scopeWrite = "write";
//
//    @Value("${security.jwt.resource-ids}")
//    private String resourceIds;
//
//    private final TokenStore tokenStore;
//
//    private final JwtAccessTokenConverter accessTokenConverter;
//
//    private final AuthenticationManager authenticationManager;
//
//    @Autowired
//    public AuthorizationServerConfig(TokenStore tokenStore, JwtAccessTokenConverter accessTokenConverter, AuthenticationManager authenticationManager) {
//        this.tokenStore = tokenStore;
//        this.accessTokenConverter = accessTokenConverter;
//        this.authenticationManager = authenticationManager;
//    }
//
//    @Override
//    public void configure(ClientDetailsServiceConfigurer configurer) throws Exception {
//        configurer
//                .inMemory()
//                .withClient(clientId)
//                .secret(clientSecret)
//                .authorizedGrantTypes(grantType)
//                .scopes(scopeRead, scopeWrite)
//                .resourceIds(resourceIds);
//    }
//
//    @Override
//    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
//        enhancerChain.setTokenEnhancers(Collections.singletonList(accessTokenConverter));
//        endpoints.tokenStore(tokenStore)
//                .accessTokenConverter(accessTokenConverter)
//                .tokenEnhancer(enhancerChain)
//                .authenticationManager(authenticationManager);
//    }
//
//}
