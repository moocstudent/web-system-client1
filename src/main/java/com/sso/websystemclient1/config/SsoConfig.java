package com.sso.websystemclient1.config;

import com.sso.webssocore.conf.Conf;
import com.sso.webssocore.filter.WebSsoWebFilter;
import com.sso.webssocore.util.JedisUtil;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置sso单点登录中心的配置类
 */
@Configuration
public class SsoConfig implements DisposableBean {

    @Value("${web.sso.server}")
    private String webSsoServer;
    @Value("${web.sso.logout.path}")
    private String webSsologoutPath;
    @Value("${web.sso.excluded.paths}")
    private String webSsoExcludedpaths;
    @Value("${web.sso.redis.address}")
    private String webSsoRedisAddress;

    @Bean
    public FilterRegistrationBean webSsoFilterRegistration(){
        //web-sso, redis init
        JedisUtil.init(webSsoRedisAddress);
        //web-sso, filter init
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setName("WebSsoWebFilter");
        registration.setOrder(1);
        registration.addUrlPatterns("/*");
        registration.setFilter(new WebSsoWebFilter());
        registration.addInitParameter(Conf.SSO_SERVER,webSsoServer);
        registration.addInitParameter(Conf.SSO_LOGOUT_PATH,webSsologoutPath);
        registration.addInitParameter(Conf.SSO_EXCLUDED_PATHS,webSsoExcludedpaths);

        return registration;
    }

    @Override
    public void destroy() throws Exception {
        //web-sso, redis close
        JedisUtil.close();
    }
}
