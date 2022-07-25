package com.springboot.oauth2.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.springboot.oauth2.service.UserDetailsServiceImpl;
import com.springboot.oauth2.utils.JwtTokenEnhancer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 授权服务器
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 密码模式
     */
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    /**
     * redis
     */
    @Autowired
    public RedisConnectionFactory redisConnectionFactory;

    /**
     *Jwt配置类
     */
    @Autowired
    JwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired
    TokenStore tokenStore;

    @Autowired
    JwtTokenEnhancer jwtTokenEnhancer;

    @Resource(name = "DruidDataSource")
    private DruidDataSource dataSource;

    //密码模式需要配置
//    @Override
//    public void configure(AuthorizationServerEndpointsConfigurer endpoints)
//            throws Exception {
//        endpoints.authenticationManager(authenticationManager)
//                .userDetailsService(userDetailsServiceImpl)
//                .tokenStore(tokenStore)
//                .accessTokenConverter(jwtAccessTokenConverter);
//    }

    //密码模式需要配置
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints)
            throws Exception {
        //创建TokenEnhancerChain实例
        TokenEnhancerChain tokenEnhancerChain=new TokenEnhancerChain();
        List<TokenEnhancer> enhancerList=new ArrayList<>();
        //配置jtv内容增强器
        enhancerList.add(jwtTokenEnhancer);
        enhancerList.add(jwtAccessTokenConverter);
        tokenEnhancerChain.setTokenEnhancers(enhancerList);
        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(userDetailsServiceImpl)
                .tokenStore(tokenStore)
                .accessTokenConverter(jwtAccessTokenConverter)
                .tokenEnhancer(tokenEnhancerChain);
    }


//    @Bean
//    public TokenStore tokenstore() {
//        RedisTokenStore redisTokenStore = new RedisTokenStore(redisConnectionFactory);
//        redisTokenStore.setPrefix("auth-token:");
//        return redisTokenStore;
//    }


    /**
     * 写死在代码里（硬编码）
     * @param clients
     * @throws Exception
     */
//    @Override
//    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        clients.inMemory()//内存中
//                .withClient("client")//客户端ID
//                .secret(passwordEncoder.encode("zk2000208"))//秘钥
//                .accessTokenValiditySeconds(60)
//                .refreshTokenValiditySeconds(3600)
//                .redirectUris("https://www.bilibili.com")//重定向到的地址
//                .scopes("all")//授权范围
//                .autoApprove(false)
//                .authorizedGrantTypes("authorization_code","password","refresh_token");//授权类型为授权码模式,密码模式,刷新令牌
//
//    }

    /**
     * 数据库读取配置
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(new JdbcClientDetailsService(dataSource));
    }


    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {

    }


}
