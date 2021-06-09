package com.example.demo.config;

import com.example.demo.filter.JwtRequestFilter;
import com.example.demo.model.enumaration.UserRole;
import com.example.demo.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.RoleHierarchyVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.Filter;
import java.util.List;

@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserServiceImpl userDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .expressionHandler(webSecurityExpressionHandler())
                .antMatchers("/auth").permitAll()
                //.antMatchers("/users/**").hasRole(String.valueOf(UserRole.CUSTOMER))
                //.antMatchers("/products/**").hasRole(String.valueOf(UserRole.PRODUCT_OWNER))
                .anyRequest().authenticated();


        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

    }

//    @Bean
//    public BCryptPasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }


    /**
     * This bean is required when using hierarchical roles.
     * Otherwise role resolution will not work reliability.
     *
     * @return the role hierarchy voter
     */
    @Bean
    protected RoleVoter roleVoter(@Qualifier("r2") RoleHierarchy roleHierarchy) {
        return new RoleHierarchyVoter(roleHierarchy);
    }

    @Bean("r1")
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        String n = UserRole.getRoleHierarchy();
       // System.out.println(n); //ROLE_ADMIN > ROLE_PRODUCT_OWNER > ROLE_CUSTOMER
        roleHierarchy.setHierarchy(n);
        return roleHierarchy;
    }

    @Bean("r2")
    @Primary
    public RoleHierarchy roleHierarchyList() {
        CustomRoleHierachyImpl roleHierarchy = new CustomRoleHierachyImpl();
        List<String> roleHierarchyList = UserRole.getRoleHierarchyList();
        System.out.println(roleHierarchyList.toString()); //[ROLE_ADMIN > ROLE_PRODUCT_OWNER, ROLE_ADMIN > ROLE_CUSTOMER]
       // roleHierarchyList.forEach(roleHierarchy::addRoleHierarchy);
        for(String w : roleHierarchyList){
            roleHierarchy.addRoleHierarchy(w);
        }
        return roleHierarchy;
    }


    public SecurityExpressionHandler<FilterInvocation> webSecurityExpressionHandler() {
        DefaultWebSecurityExpressionHandler defaultWebSecurityExpressionHandler = new DefaultWebSecurityExpressionHandler();
        defaultWebSecurityExpressionHandler.setRoleHierarchy(roleHierarchyList());
        return defaultWebSecurityExpressionHandler;
    }

}
