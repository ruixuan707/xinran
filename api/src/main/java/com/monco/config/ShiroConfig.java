package com.monco.config;

import com.monco.shiro.JwtTokenFilter;
import com.monco.shiro.UserAuthRealm;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

/**
 * ShiroConfig
 *
 * @author Lijin
 * @version 1.0.0
 */
@Configuration
public class ShiroConfig {

    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager(UserAuthRealm userAuthRealm) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(userAuthRealm);
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        manager.setSubjectDAO(subjectDAO);
        return manager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        Map<String, Filter> filterMap = new HashMap<>();
        filterMap.put("jwtToken", new JwtTokenFilter());
        factoryBean.setFilters(filterMap);
        factoryBean.setSecurityManager(defaultWebSecurityManager);
        Map<String, String> filterRuleMap = new HashMap<>();
        /* 登录接口 **/
        filterRuleMap.put("/login", "anon");
        /* 下载 **/
        filterRuleMap.put("/download", "anon");
        /* 连接池 **/
        filterRuleMap.put("/druid/**", "anon");
        /* 注册 **/
        filterRuleMap.put("/user/register**", "anon");
        /* 所有房源信息 **/
        filterRuleMap.put("/room-info/list", "anon");
        /* fastDFS上传 **/
        filterRuleMap.put("/file/uplfmdFileToFastList", "anon");
        /* swagger **/
        filterRuleMap.put("/swagger-ui.html", "anon");
        filterRuleMap.put("/webjars/**", "anon");
        filterRuleMap.put("/v2/**", "anon");
        filterRuleMap.put("/swagger-resources/**", "anon");
        filterRuleMap.put("/**", "jwtToken");
        factoryBean.setFilterChainDefinitionMap(filterRuleMap);
        return factoryBean;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager defaultWebSecurityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(defaultWebSecurityManager);
        return advisor;
    }
}